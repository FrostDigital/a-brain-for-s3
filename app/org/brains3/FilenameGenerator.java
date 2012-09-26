package org.brains3;

import java.util.UUID;

// Generates a UID which will replace the original filename.
public class FilenameGenerator {

    private final static String UID = "\\{uid\\}";
    private final static String ORIGINAL_FILENAME = "\\{orig\\}";

    public static String generate(String pattern, String originalFilename, String uid) {
        originalFilename = originalFilename != null ? removeFileExt(originalFilename) : null;
        return pattern.toLowerCase()
                .replaceAll(UID, uid)
                .replaceAll(ORIGINAL_FILENAME, originalFilename);
    }

    public static String uid() {
        return UUID.randomUUID().getMostSignificantBits() + "";
    }

    private static String removeFileExt(String filename) {
        int i = filename.lastIndexOf(".");
        return i > 0 ? filename.substring(0, i) : filename;
    }

}