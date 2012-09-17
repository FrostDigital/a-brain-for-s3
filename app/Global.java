import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.brains3.ConfigParser;
import play.Application;
import play.GlobalSettings;
import play.Logger;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: Joel Soderstrom (joel[at]frostdigital[dot]se)
 * Date: 2012-09-05
 * Time: 10:34 PM
 */
public class Global extends GlobalSettings {

    @Override
    public void onStart(Application app) {
        if(app.isTest()) {
            return;
        }

        //String brainConfLocation = app.configuration().getString("brain-conf");

        /*if(brainConfLocation == null || brainConfLocation.isEmpty()) {
            Logger.warn("No brain configuration was loaded, is 'brain-conf' set?");
        } else {*/
            //loadBrainConf(brainConfLocation);
            ConfigParser.parseConfig(play.api.Play.unsafeApplication().configuration().underlying());
        //}

    }

    /**
     * Load brain configuration at given location.
     * @param location - filename or URL
     */
    private void loadBrainConf(String location) {
        Logger.info("Loading brain.conf from: " + location);

        // TODO: This is probably already solved more elegantly
        // somewhere in Typesafe Config lib

        String path = location;
        Config config = null;

        if(location.startsWith("http://")) {
            try {
                config = ConfigFactory.parseURL(new URL(location));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        else if(!location.startsWith("/")) {
            config = ConfigFactory.parseResources(location);
        }
        else {
            config = ConfigFactory.parseFile(new File(location));
        }

        //ConfigParser.parseConfig(config);
    }
}
