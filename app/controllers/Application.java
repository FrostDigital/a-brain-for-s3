package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

public class Application extends Controller {
  
    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }

    public static Result settings() {
        return ok(index.render("Your new application is ready."));
    }

    public static Result testUpload() {
        return ok(uploadForm.render());
    }

}