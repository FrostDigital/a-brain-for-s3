package org.brains3;

import play.cache.Cache;

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
        return (Preset) Cache.get("preset." + presetName);
    }

    public static void savePreset(Preset preset) {
        Cache.set("preset." + preset.name, preset);
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
