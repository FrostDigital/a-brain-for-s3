import com.typesafe.config.ConfigFactory;
import org.brains3.ConfigParser;
import play.Application;
import play.GlobalSettings;
import play.Logger;

import java.io.File;

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

        String brainConfLocation = app.configuration().getString("brain-conf");

        if(brainConfLocation == null || brainConfLocation.isEmpty()) {
            Logger.warn("No brain configuration was loaded, is 'brain-conf' set?");
        } else {
            loadBrainConf(brainConfLocation);
        }

    }

    /**
     * Load brain configuration at given location.
     * @param location - filename or URL
     */
    private void loadBrainConf(String location) {
        Logger.info("Loading brain.conf from: " + location);
        ConfigParser.parseConfig( ConfigFactory.parseFileAnySyntax(new File(location)));
    }
}
