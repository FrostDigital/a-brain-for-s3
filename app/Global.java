import org.brains3.ConfigParser;
import play.Application;
import play.GlobalSettings;
import play.Logger;

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
            ConfigParser.parseConfig(brainConfLocation);
        }
    }

    /**
     * Load brain configuration at given location.
     * @param location - filename or URL
     */
    /*private Config loadBrainConf(String location, ClassLoader classLoader) {
        Logger.info("Loading brain.conf from: " + location);

        String[] locations = location.split(",");
        for (String l : locations) {
            l.trim();

            ConfigFactory.parseResourcesAnySyntax(l);
        }

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
            config = ConfigFactory.parseResources(classLoader, location);
        }
        else {
            config = ConfigFactory.parseFile(new File(location));
        }

        Logger.debug(config.toString());

        return config;
    } */
}
