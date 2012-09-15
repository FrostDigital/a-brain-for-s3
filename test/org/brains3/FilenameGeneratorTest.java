package org.brains3;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: Joel Soderstrom (joel[at]frostdigital[dot]se)
 * Date: 2012-09-09
 * Time: 12:21 PM
 */
public class FilenameGeneratorTest {

    @Test
    public void testParse() throws Exception {
        assertThat(FilenameGenerator.parse("UID")).isEqualTo(FilenameGenerator.UID);
        assertThat(FilenameGenerator.parse("none")).isEqualTo(FilenameGenerator.NONE);
        //assertThat(FilenameGenerator.parse("prepend_timestamp ")).isEqualTo(FilenameGenerator.PREPEND_TIMESTAMP);
        assertThat(FilenameGenerator.parse("prepend-uid")).isEqualTo(FilenameGenerator.PREPEND_UID);
        assertThat(FilenameGenerator.parse("prepend-uid_dir")).isEqualTo(FilenameGenerator.PREPEND_UID_DIR);
        assertThat(FilenameGenerator.parse(null)).isEqualTo(FilenameGenerator.NONE);
    }

    @Test
    public void testGenerateFilename_uid() throws Exception {
        String filename = FilenameGenerator.UID.generate("foo.png", FileFormat.GIF);
        assertThat(filename).endsWith(".gif");
        assertThat(filename.length()).isGreaterThan(10);
    }

    @Test
    public void testGenerateFilename_none() throws Exception {
        String filename = FilenameGenerator.NONE.generate("foo.png", FileFormat.GIF);
        assertThat(filename).isEqualTo("foo.gif");

        filename = FilenameGenerator.NONE.generate("foo", FileFormat.GIF);
        assertThat(filename).isEqualTo("foo.gif");
    }

    @Test
    public void testGenerateFilename_prependUid() throws Exception {
        String filename = FilenameGenerator.PREPEND_UID.generate("foo.png", FileFormat.GIF);
        assertThat(filename).endsWith("_foo.gif");
        assertThat(filename.length()).isGreaterThan(10);
    }

    @Test
    public void testGenerateFilename_prependUidDir() throws Exception {
        String filename = FilenameGenerator.PREPEND_UID_DIR.generate("foo.png", FileFormat.GIF);
        assertThat(filename).endsWith("/foo.gif");
        assertThat(filename.length()).isGreaterThan(10);
    }

}
