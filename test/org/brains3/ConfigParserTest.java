package org.brains3;

import com.typesafe.config.ConfigFactory;
import org.junit.Test;

import java.io.File;

import static org.fest.assertions.Assertions.assertThat;


/**
 * Created with IntelliJ IDEA.
 * User: Joel Soderstrom (joel[at]frostdigital[dot]se)
 * Date: 2012-09-08
 * Time: 11:20 PM
 */
public class ConfigParserTest extends BaseIntegrationTest {

    @Test
    public void testParseConfig() throws Exception {
        ConfigParser.parseConfig(ConfigFactory.parseFile(new File("conf/test-conf.conf")));

        Preset thumbPreset = Preset.getPreset("thumb");

        assertThat(thumbPreset).isNotNull();
        assertThat(thumbPreset.name).isEqualTo("thumb");
        assertThat(thumbPreset.filenameGenerator).isEqualTo(FilenameGenerator.UID);
        assertThat(thumbPreset.format).isEqualTo(FileFormat.JPG);
        assertThat(thumbPreset.resizeStrategy).isEqualTo(ResizeStrategy.FIT);
        assertThat(thumbPreset.width).isEqualTo(100);
        assertThat(thumbPreset.height).isEqualTo(100);


        Bucket myBucket = Bucket.getBucket("my-bucket");

        assertThat(myBucket).isNotNull();
        assertThat(myBucket.alias).isEqualTo("my-bucket");
        assertThat(myBucket.key).isEqualTo("BKIAIS6BXQ7U72DQLYRQ");
        assertThat(myBucket.secret).isEqualTo("nVoRnbUHhdhIjTfRY5mQ+onwf9zvlMEwUANxGr5Y");
        assertThat(myBucket.name).isEqualTo("my-bucket-name");
        assertThat(myBucket.publicUrl).isEqualTo("http://bucket-url.s3-website-eu-west-1.amazonaws.com/");
    }

    @Test
    public void testParseConfig_multipleLocations() throws Exception {
        ConfigParser.parseConfig("test-conf.conf,test-conf2.conf");

        Bucket myBucket = Bucket.getBucket("my-bucket");
        assertThat(myBucket.key).isEqualTo("OVERRIDDEN");
    }

}
