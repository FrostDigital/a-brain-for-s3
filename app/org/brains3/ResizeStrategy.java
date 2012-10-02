package org.brains3;

import org.apache.commons.lang.StringUtils;

/**
 * Created with IntelliJ IDEA.
 * User: Joel Soderstrom (joel[at]frostdigital[dot]se)
 * Date: 2012-09-09
 * Time: 3:37 PM
 */
public enum ResizeStrategy {
    FIT,
    FIT_TO_WIDTH,
    FIT_TO_HEIGHT,
    STRETCH,
    PAD,
    CROP,
    FILLCROP;

    public static ResizeStrategy parse(String str) {
        if(StringUtils.isBlank(str)) {
            return null;
        }
        return valueOf(str.toUpperCase().trim());
    }

}
