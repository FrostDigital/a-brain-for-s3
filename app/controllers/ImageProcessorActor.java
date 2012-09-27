package controllers;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.routing.RoundRobinRouter;
import org.brains3.ImageProcessRequest;
import org.brains3.ProcessedImage;
import org.brains3.S3Client;
import org.brains3.image.ImgScalrProcessor;
import play.Logger;
import play.libs.Akka;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Joel Soderstrom (joel[at]frostdigital[dot]se)
 * Date: 2012-09-24
 * Time: 10:44 PM
 */
public class ImageProcessorActor extends UntypedActor {

    public static ActorRef instance = Akka.system().actorOf(new Props(ImageProcessorActor.class).withRouter(new RoundRobinRouter(50)));

    @Override
    public void onReceive(Object o) {
        if(o instanceof ImageProcessRequest) {
            ImageProcessRequest message = (ImageProcessRequest) o;

            Long start = System.currentTimeMillis();
            Logger.debug("------------- Start processing " + message.generatedFilename);

            try {
                ProcessedImage processedImage = new ImgScalrProcessor().process(message);
                S3Client.uploadFile(processedImage, message);
            } catch (IOException e) {
                Logger.warn("Could not process or upload image: " + e.getMessage());
            }

            Logger.debug("------------- End processing   " + message.generatedFilename + " (" + (System.currentTimeMillis() - start) + " ms)");
        }
        else {
            unhandled(o);
        }
    }

    public static void processImage(ImageProcessRequest processRequest) {
        instance.tell(processRequest);
    }

}
