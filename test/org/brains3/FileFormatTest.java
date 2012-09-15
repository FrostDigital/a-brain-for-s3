package org.brains3;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: Joel Soderstrom (joel[at]frostdigital[dot]se)
 * Date: 2012-09-09
 * Time: 2:56 PM
 */
public class FileFormatTest {

    @Test
    public void testParse() throws Exception {
        assertThat(FileFormat.parse("gif")).isEqualTo(FileFormat.GIF);
        assertThat(FileFormat.parse("TIFF")).isEqualTo(FileFormat.TIFF);
        assertThat(FileFormat.parse("png ")).isEqualTo(FileFormat.PNG);
        assertThat(FileFormat.parse("bmp")).isEqualTo(FileFormat.BMP);
        assertThat(FileFormat.parse("jpg")).isEqualTo(FileFormat.JPG);
        assertThat(FileFormat.parse("jpeg")).isEqualTo(FileFormat.JPG);
        assertThat(FileFormat.parse(null)).isNull();
        assertThat(FileFormat.parse("")).isNull();
        assertThat(FileFormat.parse(" ")).isNull();
    }
}
