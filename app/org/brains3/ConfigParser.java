package org.brains3;

import com.typesafe.config.Config;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Joel Soderstrom (joel[at]frostdigital[dot]se)
 * Date: 2012-09-08
 * Time: 11:12 PM
 */
public class ConfigParser {

    private static final String PRESETS_KEY = "brain-conf.presets";
    private static final String BUCKETS_KEY = "brain-conf.buckets";

    public static void parseConfig(Config config) {
        parseBuckets(config.getConfig(BUCKETS_KEY));
        parsePresets(config.getConfig(PRESETS_KEY));
    }

    private static void parsePresets(Config presets) {
        for(Map.Entry<String, Object> e : presets.root().unwrapped().entrySet()) {
            final String presetName = e.getKey();
            Config presetConf = presets.getConfig(presetName);

            Preset.savePreset(new Preset(
                    presetName,
                    presetConf.getInt("width"),
                    presetConf.getInt("height"),
                    ResizeStrategy.parse(presetConf.getString("resize-strategy")),
                    ScaleMethod.parse(presetConf.getString("optimize-scaling-for")),
                    FilenameGenerator.parse(presetConf.getString("filename-generator")),
                    FileFormat.parse(presetConf.getString("format")),
                    presetConf.getInt("compression-level")
            ));
        }
    }

    private static void parseBuckets(Config buckets) {
        for(Map.Entry<String, Object> e : buckets.root().unwrapped().entrySet()) {
            final String bucketName = e.getKey();
            Config bucketConf = buckets.getConfig(bucketName);

            Bucket.saveBucket(new Bucket(
                    bucketName,
                    bucketConf.getString("name"),
                    bucketConf.getString("key"),
                    bucketConf.getString("secret"),
                    bucketConf.getString("public-url")
            ));
        }
    }

}
