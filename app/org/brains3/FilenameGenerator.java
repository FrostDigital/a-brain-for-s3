package org.brains3;

import org.apache.commons.lang.StringUtils;

import java.util.UUID;

// Generates a UID which will replace the original filename.
public enum FilenameGenerator {

    // Example:
    // foo.png -> 503cac96c5b7a11c7317b0cc.png
    UID,

    // Prepends a generated UID to original filename. Example:
    // foo.png -> 503cac96c5b7a11c7317b0cc_foo.png
    PREPEND_UID,

    // Generate a UID directory and preserves the original filename:
    // foo.png -> 503cac96c5b7a11c7317b0cc/foo.png
    PREPEND_UID_DIR,

    // Preserves the original filename. Note that this approach could
    // very likely lead to name clashes. Example:
    // foo.png -> foo.png
    NONE;

    private static final String DELIMITER = "_";
    private static final String DIR_DELIMITER = "/";


    public static FilenameGenerator parse(String str) {
        if(StringUtils.isBlank(str)) {
            return NONE;
        }
        return valueOf(str.toUpperCase().trim().replaceAll("-", "_").replaceAll(" ", "_"));
    }

    public String generate(String originalFilename, FileFormat fileFormat) {
        String generatedFilename = null;
        switch (this) {
            case UID: generatedFilename = uid(); break;
            case PREPEND_UID: generatedFilename = prependUID(originalFilename); break;
            case PREPEND_UID_DIR: generatedFilename = prependUIDDir(originalFilename); break;
            default: generatedFilename = none(originalFilename);
        }

        return generatedFilename + "." + fileFormat.name().toLowerCase();
    }

    private String uid() {
        return UUID.randomUUID().toString();
    }

    private String prependUID(String originalFilename) {
        originalFilename = removeFileExt(originalFilename);
        return UUID.randomUUID().toString() + DELIMITER + originalFilename;
    }

    private String none(String originalFilename) {
        return removeFileExt(originalFilename);
    }

    private String prependUIDDir(String originalFilename) {
        originalFilename = removeFileExt(originalFilename);
        return uid() + DIR_DELIMITER + originalFilename;
    }

    private String removeFileExt(String filename) {
        int i = filename.lastIndexOf(".");
        return i > 0 ? filename.substring(0, i) : filename;
    }

}