package controllers;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import play.Logger;
import play.libs.Akka;

/**
 * Created with IntelliJ IDEA.
 * User: Joel Soderstrom (joel[at]frostdigital[dot]se)
 * Date: 2012-09-24
 * Time: 10:44 PM
 */
public class ImageProcessorActor extends UntypedActor {

    public static ActorRef instance = Akka.system().actorOf(new Props(ImageProcessorActor.class));


    @Override
    public void onReceive(Object message) {
        Logger.debug(message + "");
    }
}
