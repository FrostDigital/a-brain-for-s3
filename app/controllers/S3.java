package controllers;

import org.brains3.*;
import org.brains3.image.ImgScalrProcessor;
import play.Logger;
import play.libs.Akka;
import play.libs.F;
import play.libs.F.Promise;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

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
        // TODO: Stream body http://stackoverflow.com/questions/12066993/uploading-file-as-stream-in-play-framework-2-0?lq=1

        // Validate request
        Bucket bucket = Bucket.getBucket(bucketName);
        if(bucket == null) {
            return notFound("Bucket " + bucketName + " does not exist");
        }

        Set<Preset> presets = new HashSet<Preset>();
        for(String p : presetNames.split(",")) {
            presets.add(Preset.getPreset(p));
        }

        if(presets.isEmpty()) {
            return notFound("Preset(s) " + presetNames + " does not exist");
        }

		
        final List<ImageProcessRequest> imageProcessRequests = new ArrayList<ImageProcessRequest>();
        List<Promise<Boolean>> tmpPromises = new ArrayList<Promise<Boolean>>();

        for(Http.MultipartFormData.FilePart filePart : request().body().asMultipartFormData().getFiles()) {
            for(ImageProcessRequest req : prepareProcessing(bucket, presets, filePart))  {
                tmpPromises.add(createImageProcessPromise(req));
                imageProcessRequests.add(req);
            }
        }

        Promise<List<Boolean>> promises = F.Promise.waitAll(tmpPromises);
		
		final Http.Response resp = response();
		
        F.Promise<Result> result = promises.map(new F.Function<List<Boolean>, Result>() {
            @Override
            public Result apply(List<Boolean> responses) throws Throwable {
                //for(Boolean success : responses) { }
				cors(resp);
                return ok(Json.toJson(imageProcessRequests));
            }
        });

        return async(result);
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

    private static Promise<Boolean> createImageProcessPromise(final ImageProcessRequest imageProcessRequest) {
        return Akka.future(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {

                Long start = System.currentTimeMillis();
                Logger.debug("------------- Start processing " + imageProcessRequest.generatedFilename);

                try {
                    ProcessedImage processedImage = new ImgScalrProcessor().process(imageProcessRequest);
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
}