package org.brains3;

import play.cache.Cache;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Joel Soderstrom (joel[at]frostdigital[dot]se)
 * Date: 2012-09-08
 * Time: 11:01 PM
 */
public class Preset {

    public final String name;
    public final int width;
    public final int height;
    public final ScaleMethod scaleMethod;
    public final ResizeStrategy resizeStrategy;
    public final FilenameGenerator filenameGenerator;
    public final FileFormat format;
    public final Integer compressionLevel;

    public Preset(String name, int width, int height, ResizeStrategy resizeStrategy, ScaleMethod scaleMethod,
                  FilenameGenerator filenameGenerator, FileFormat format, Integer compressionLevel) {
        this.name = name;
        this.width = width;
        this.height = height;
        this.scaleMethod = scaleMethod;
        this.resizeStrategy = resizeStrategy;
        this.filenameGenerator = filenameGenerator;
        this.format = format;
        this.compressionLevel = compressionLevel;
    }

    public static Preset getPreset(final String presetName) {
        Map<String, Preset> presets = (Map<String, Preset>) Cache.get("presets");
        return presets != null ? presets.get(presetName) : null;
    }

    public static void savePreset(Preset preset) {
        Map<String, Preset> presets = (Map<String, Preset>) Cache.get("presets");
        if(presets == null) {
            presets = new HashMap<String, Preset>();
            Cache.set("presets", presets);
        }
        presets.put(preset.name, preset);
    }

    public static List<Preset> getAll() {
        Map<String, Preset> presets = (Map<String, Preset>) Cache.get("presets");
        return presets != null ? new ArrayList<Preset>(presets.values()) : Collections.<Preset>emptyList();
    }

    @Override
    public String toString() {
        return name + " {"+
                ", width=" + width +
                ", height=" + height +
                ", scaleMethod=" + scaleMethod +
                ", resizeStrategy=" + resizeStrategy +
                ", filenameGenerator=" + filenameGenerator +
                ", format=" + format +
                ", compressionLevel=" + compressionLevel +
                '}';
    }
}
