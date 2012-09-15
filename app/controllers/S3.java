package controllers;

import org.brains3.Bucket;
import org.brains3.Preset;
import org.brains3.ProcessedImage;
import org.brains3.S3Client;
import org.brains3.image.ImgScalrProcessor;
import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import java.io.IOException;

public class S3 extends Controller {

    public static Result getFile(String bucket, String filename) {
        request().queryString();
        return null;
    }

    public static Result create(String bucketName, String presetName) {
        // Validate request
        Bucket bucket = Bucket.getBucket(bucketName);
        if(bucket == null) {
            return notFound("Bucket " + bucketName + " does not exist");
        }
        Preset preset = Preset.getPreset(presetName);
        if(preset == null) {
            return notFound("Preset " + presetName + " does not exist");
        }

        Http.MultipartFormData.FilePart filePart = request().body().asMultipartFormData().getFile("file");
        if (filePart == null) {
            return badRequest("No file was POSTed");
        }

        ProcessedImage processedImage = new ProcessedImage(preset, filePart.getFilename());

        // Perform image processing
        try {
            processedImage = new ImgScalrProcessor().process(processedImage, filePart.getFile());
        } catch (IOException e) {
            Logger.warn("Could not process image: " + e.getMessage());
            return internalServerError("Could not process image: " + e.getMessage());
        }

        // Upload file(s)
        try {
            S3Client.uploadFile(bucket, processedImage);
        } catch (IOException e) {
            Logger.warn("Could not upload image: " + e.getMessage());
            return internalServerError("Could not upload image: " + e.getMessage());
        }

        return ok(Json.toJson(processedImage));
    }

    public static class UploadResponse {
        public String message;
        public String fileUrl;
    }

}