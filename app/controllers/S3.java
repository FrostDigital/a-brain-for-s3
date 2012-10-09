package controllers;

import org.apache.commons.lang.StringUtils;
import org.brains3.*;
import org.brains3.image.ImgScalrProcessor;
import org.codehaus.jackson.JsonNode;
import play.Logger;
import play.cache.Cache;
import play.libs.Akka;
import play.libs.F;
import play.libs.F.Promise;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

public class S3 extends Controller {

    public static Result preflightRequest(String bucket, String preset) {
        cors(response());
        return ok();
    }

    public static Result upload(String bucketName, String presetNames) {
        // Validate request
        Bucket bucket = Bucket.getBucket(bucketName);
        if(bucket == null) {
            return notFound("Bucket " + bucketName + " does not exist");
        }

        final String id = request().queryString().containsKey("id")
                ? request().queryString().get("id")[0] : null ;

        Set<Preset> presets = new HashSet<Preset>();
        for(String p : presetNames.split(",")) {
            presets.add(Preset.getPreset(p));
        }

        if(presets.isEmpty()) {
            return notFound("Preset(s) " + presetNames + " does not exist");
        }

        final List<ImageProcessRequest> imageProcessRequests = new ArrayList<ImageProcessRequest>();
        final List<BufferedImage> images = new ArrayList<BufferedImage>();
        List<Promise<Boolean>> tmpPromises = new ArrayList<Promise<Boolean>>();


        for(Http.MultipartFormData.FilePart filePart : request().body().asMultipartFormData().getFiles()) {
            BufferedImage img = readImage(filePart.getFile(), filePart.getFilename());
            images.add(img);
            for(ImageProcessRequest req : prepareProcessing(bucket, presets, filePart))  {
                tmpPromises.add(createImageProcessPromise(req, img));
                imageProcessRequests.add(req);
            }
        }

        Promise<List<Boolean>> promises = Promise.waitAll(tmpPromises.toArray( new Promise[]{} ));
		
		final Http.Response resp = response();

        F.Promise<Result> result = promises.map(new F.Function<List<Boolean>, Result>() {
            @Override
            public Result apply(List<Boolean> responses) throws Throwable {
				cors(resp);
                flush(images);

                // TODO: Make this configurable
                // Set content type to text/plain to fix issue with IE:
                // https://github.com/valums/file-uploader#internet-explorer-limitations
                resp.setContentType("text/plain");

                JsonNode json = Json.toJson(imageProcessRequests);

                if(!StringUtils.isBlank(id)) {
                    cacheResult(id, json);
                }
                return ok(json);
            }
        });

        return async(result);
    }

    public static Result result(String id) {
        cors(response());
        return ok(getCachedResult(id));
    }

    private static List<ImageProcessRequest> prepareProcessing(Bucket bucket, Set<Preset> presets, Http.MultipartFormData.FilePart filePart) {
        List<ImageProcessRequest> imageProcessRequests = new ArrayList<ImageProcessRequest>();
        String sharedUID = FilenameGenerator.uid();

        for(Preset preset : presets) {
            imageProcessRequests.add(new ImageProcessRequest(
                    preset,
                    bucket,
                    filePart.getFile(),
                    filePart.getFilename(),
                    FilenameGenerator.generate(preset.filenamePattern, filePart.getFilename(), sharedUID)
            ));
        }

        return imageProcessRequests;
    }

    private static void cors(Http.Response response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "OPTIONS, HEAD, GET, POST, PUT, DELETE");
        response.setHeader("Access-Control-Allow-Headers", "origin, x-mime-type, x-requested-with, x-file-name, content-type");
    }

    private static Promise<Boolean> createImageProcessPromise(final ImageProcessRequest imageProcessRequest, final BufferedImage image) {
        return Akka.future(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {

                Long start = System.currentTimeMillis();
                Logger.debug("------------- Start processing " + imageProcessRequest.generatedFilename);

                try {
                    ProcessedImage processedImage = new ImgScalrProcessor().process(imageProcessRequest, image);
                    S3Client.uploadFile(processedImage, imageProcessRequest);
                } catch (IOException e) {
                    Logger.warn("Could not process or upload image: " + e.getMessage());
                    return false;
                }

                Logger.debug("------------- End processing   " + imageProcessRequest.generatedFilename + " (" + (System.currentTimeMillis() - start) + " ms)");

                return true;
            }
        });
    }

    private static BufferedImage readImage(File file, String originalFilename) {
        BufferedImage bufferedImage = null;
        try {
            Long start = System.currentTimeMillis();
            bufferedImage = ImageIO.read(file);
            Logger.debug("Finished reading file to memory " + originalFilename + " (" + (System.currentTimeMillis() - start) + " ms)");
        } catch (IOException e) {
            Logger.warn("Could not read Image: " + originalFilename);
        }

        return bufferedImage;
    }

    private static void flush(List<BufferedImage> images) {
        for (BufferedImage img : images) {
            img.flush();
        }
    }

    private static final String RESULT_CACHE_PREFIX = "processed-image.";
    private static final int CACHE_RESULT_EXPIRES = 60*2; // 2 min

    private static void cacheResult(String key, JsonNode json) {
        Cache.set(RESULT_CACHE_PREFIX + key, json, CACHE_RESULT_EXPIRES);
    }

    private static JsonNode getCachedResult(String key) {
        return (JsonNode) Cache.get(RESULT_CACHE_PREFIX + key);
    }
}