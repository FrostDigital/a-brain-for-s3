package org.brains3;

import org.apache.commons.lang.StringUtils;

/**
 * Created with IntelliJ IDEA.
 * User: Joel Soderstrom (joel[at]frostdigital[dot]se)
 * Date: 2012-09-09
 * Time: 2:54 PM
 */
public enum FileFormat {
    JPG("jpg jpeg", "image/jpeg"),
    PNG("png", "image/png"),
    BMP("bmp", "image/bmp"),
    GIF("gif", "image/gif"),
    TIFF("tif tiff", "image/tiff");

    public final String aliases;
    public final String contentType;

    FileFormat(String aliases, String contentType) {
        this.aliases = aliases;
        this.contentType = contentType;
    }

    public static FileFormat parse(String str) {
        if(!StringUtils.isBlank(str) && str.length() > 2) {
            str = str.toLowerCase().trim();

            for (FileFormat ff : FileFormat.values()) {
                if (ff.aliases.contains(str)) {
                    return ff;
                }
            }
        }

        return null;
    }
}