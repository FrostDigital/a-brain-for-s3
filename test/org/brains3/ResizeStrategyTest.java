package org.brains3;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: Joel Soderstrom (joel[at]frostdigital[dot]se)
 * Date: 2012-09-09
 * Time: 3:39 PM
 */
public class ResizeStrategyTest {

    @Test
    public void testParse() throws Exception {
        assertThat(ResizeStrategy.parse("crop")).isEqualTo(ResizeStrategy.CROP);
        assertThat(ResizeStrategy.parse(" fillcrop ")).isEqualTo(ResizeStrategy.FILLCROP);
        assertThat(ResizeStrategy.parse("FIT")).isEqualTo(ResizeStrategy.FIT);
        assertThat(ResizeStrategy.parse("pad  ")).isEqualTo(ResizeStrategy.PAD);
        assertThat(ResizeStrategy.parse(" stretch")).isEqualTo(ResizeStrategy.STRETCH);
    }
}
