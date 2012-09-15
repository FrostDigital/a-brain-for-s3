package org.brains3;

import org.apache.commons.lang.StringUtils;

/**
 * Created with IntelliJ IDEA.
 * User: Joel Soderstrom (joel[at]frostdigital[dot]se)
 * Date: 2012-09-09
 * Time: 2:54 PM
 */
public enum ScaleMethod {

    AUTOMATIC("auto"),
    SPEED("speed fast"),
    MEDIUM("medium balanced"),
    QUALITY("quality"),
    HIGH_QUALITY("high-quality highquality high_quality");

    public final String aliases;

    ScaleMethod(String aliases) {
        this.aliases = aliases;
    }

    public static ScaleMethod parse(String str) {
        if(!StringUtils.isBlank(str) && str.length() > 2) {
            str = str.toLowerCase().trim();

            for (ScaleMethod ff : ScaleMethod.values()) {
                if (ff.aliases.contains(str)) {
                    return ff;
                }
            }
        }

        return AUTOMATIC;
    }
}