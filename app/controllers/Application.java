package controllers;

import org.brains3.Bucket;
import org.brains3.Preset;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;
import views.html.uploadForm;

import java.util.HashSet;
import java.util.Set;

public class Application extends Controller {
  
    public static Result index() {
        return ok(index.render());
    }

    public static Result settings() {
        return ok(
            settings.render(Preset.getAll(), Bucket.getAll())
        );
    }

    public static Result testUpload() {
        return ok(uploadForm.render(Preset.getAll(), Bucket.getAll()));
    }

    public static Result testBucketAndPreset(String bucket, String preset) {
        Bucket b = Bucket.getBucket(bucket);
        Set<Preset> presets = new HashSet<Preset>();

        for(String p : preset.split(",")) {
            presets.add(Preset.getPreset(p));
        }

        return ok("Found bucket: " + b.name + "\nFound preset(s): " + presets.toString());
    }

}