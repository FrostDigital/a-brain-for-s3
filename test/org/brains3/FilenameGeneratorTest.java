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
    public void testGenerateFilename_uid() throws Exception {
        String filename = FilenameGenerator.generate("{uid}.gif", null, "12345678");
        assertThat(filename).isEqualTo("12345678.gif");
    }

    @Test
    public void testGenerateFilename_none() throws Exception {
        String filename = FilenameGenerator.generate("{orig}.gif", "foo.jpg", null);
        assertThat(filename).isEqualTo("foo.gif");
    }

    @Test
    public void testGenerateFilename_prependUid() throws Exception {
        String filename = FilenameGenerator.generate("{uid}_{orig}.gif", "foo.jpg", "123456789");
        assertThat(filename).isEqualTo("123456789_foo.gif");

    }
}
