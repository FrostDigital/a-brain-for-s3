package controllers;

import org.brains3.Bucket;
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

    public static Result getFile(String bucket, String filename) {
        request().queryString();
        return null;
    }

    public static Result create(String bucketName, String presetNames) {
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

        Http.MultipartFormData.FilePart filePart = request().body().asMultipartFormData().getFile("file");
        if (filePart == null) {
            return badRequest("No file was POSTed");
        }

        List<ImageProcessRequest> processedImages = new ArrayList<ImageProcessRequest>();

        for(Preset preset : presets) {
            ImageProcessRequest imageProcessRequest = new ImageProcessRequest(preset, bucket, filePart.getFile(), filePart.getFilename());
            ImageProcessorActor.processImage(imageProcessRequest);
            processedImages.add(imageProcessRequest);
        }

        return ok(Json.toJson(processedImages));
    }

}