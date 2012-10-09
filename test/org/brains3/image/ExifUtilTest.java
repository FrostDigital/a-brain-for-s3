package org.brains3.image;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

/**
 * Created with IntelliJ IDEA.
 * User: Joel Soderstrom (joel[at]frostdigital[dot]se)
 * Date: 2012-10-09
 * Time: 4:12 PM
 */
public class ExifUtilTest {

    @Test
    public void testReadImageInformation_jpg8() throws Exception {
        File image = readTestImage("./test/dog.jpg");
        assertEquals(ExifUtil.readImageOrientation(image).intValue(), 8);
    }

    @Test
    public void testReadImageInformation_jpg1() throws Exception {
        File image = readTestImage("./test/large.jpg");
        assertEquals(ExifUtil.readImageOrientation(image).intValue(), 1);

    }

    @Test
    public void testReadImageInformation_png() throws Exception {
        File image = readTestImage("./test/large.png");
        assertNull(ExifUtil.readImageOrientation(image));
    }

    private File readTestImage(String filename) {
        File f = new File(filename);
        if(!f.exists()) {
             fail("Test file does not exist");
        }
        return f;
    }

}
