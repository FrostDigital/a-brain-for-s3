package controllers;

import org.brains3.Bucket;
import org.brains3.FilenameGenerator;
import org.brains3.ImageProcessRequest;
import org.brains3.Preset;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class S3 extends Controller {

    public static Result preflightRequest(String bucket, String preset) {
        // Handle request when browsers checks if CORS is possible
        cors(response());
        return ok();
    }

    public static Result upload(String bucketName, String presetNames) {
        // TODO: Stream body
        // http://stackoverflow.com/questions/12066993/uploading-file-as-stream-in-play-framework-2-0?lq=1
        // http://stackoverflow.com/questions/11468768/why-makes-calling-error-or-done-in-a-bodyparsers-iteratee-the-request-hang-in-p
        // http://www.infoq.com/presentations/Play-I-ll-See-Your-Async-and-Raise-You-Reactive

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

        List<ImageProcessRequest> imageProcessRequests = new ArrayList<ImageProcessRequest>();

        for(Http.MultipartFormData.FilePart filePart : request().body().asMultipartFormData().getFiles()) {
            imageProcessRequests.addAll(prepareProcessing(bucket, presets, filePart));
            for(ImageProcessRequest req : imageProcessRequests) {
                ImageProcessorActor.processImage(req);
            }
        }

        cors(response());

        return ok(Json.toJson(imageProcessRequests));
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
        response().setHeader("Access-Control-Allow-Origin", "*");
        response().setHeader("Access-Control-Allow-Methods", "OPTIONS, HEAD, GET, POST, PUT, DELETE");
        response().setHeader("Access-Control-Allow-Headers", "origin, x-mime-type, x-requested-with, x-file-name, content-type");
    }

}