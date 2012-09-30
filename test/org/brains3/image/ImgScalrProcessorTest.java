package org.brains3.image;

import org.brains3.*;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: Joel Soderstrom (joel[at]frostdigital[dot]se)
 * Date: 2012-09-09
 * Time: 10:42 PM
 */
public class ImgScalrProcessorTest {

    private ImageProcessor processor = new ImgScalrProcessor();

    @Test
    public void testProcess_JPGtoJPG() throws Exception {
        // GIVEN
        File img = readTestImage("./test/large.jpg");
        Preset preset = new Preset("thumb", 150, 150, ResizeStrategy.FIT, ScaleMethod.AUTOMATIC,
                "{uid}.jpg", FileFormat.JPG, 50);

        ImageProcessRequest req = new ImageProcessRequest(
                preset, bucket(), img, img.getName(), "foo");

        // WHEN
        ProcessedImage processedImage = processor.process(req);

        // THEN
        assertNotNull(processedImage);
        assertTrue(processedImage.image.exists());

        openImage(processedImage.image.getAbsolutePath());
    }

    @Test
    public void testProcess_JPGtoPNG() throws Exception {
        // GIVEN
        File img = readTestImage("./test/large.jpg");
        Preset preset = new Preset("thumb", 150, 150, ResizeStrategy.FIT, ScaleMethod.QUALITY,
                "{uid}.png", FileFormat.PNG, 100);
        ImageProcessRequest req = new ImageProcessRequest(preset, bucket(), img, img.getName(), "foo");

        // WHEN
        ProcessedImage processedImage = processedImage = processor.process(req);

        // THEN
        assertNotNull(processedImage);
        assertTrue(processedImage.image.exists());

        openImage(processedImage.image.getAbsolutePath());
    }

    @Test
    @Ignore
    public void testProcess_JPGtoBMP() throws Exception {
        // GIVEN
        File img = readTestImage("./test/large.jpg");
        Preset preset = new Preset("thumb", 150, 150, ResizeStrategy.FIT, ScaleMethod.AUTOMATIC,
                "{uid}.bmp", FileFormat.BMP, 100);
        ImageProcessRequest req = new ImageProcessRequest(
                preset, bucket(), img, img.getName(), "foo");


        // WHEN
        ProcessedImage processedImage = processedImage = processor.process(req);

        // THEN
        assertNotNull(processedImage);
        assertTrue(processedImage.image.exists());

        openImage(processedImage.image.getAbsolutePath());
    }

    @Test
    @Ignore
    public void testProcess_JPGtoGIF() throws Exception {
        // GIVEN
        File img = readTestImage("./test/large.jpg");
        Preset preset = new Preset("thumb", 150, 150, ResizeStrategy.FIT, ScaleMethod.AUTOMATIC,
                "{uid}.gif", FileFormat.GIF, 100);
        ImageProcessRequest req = new ImageProcessRequest(preset, bucket(), img, img.getName(), "foo");

        // WHEN
        ProcessedImage processedImage = processedImage = processor.process(req);

        // THEN
        assertNotNull(processedImage);
        assertTrue(processedImage.image.exists());

        openImage(processedImage.image.getAbsolutePath());
    }

    @Test
    @Ignore
    public void testProcess_JPGtoTIF() throws Exception {
        // GIVEN
        File img = readTestImage("./test/large.jpg");
        Preset preset = new Preset("thumb", 150, 150, ResizeStrategy.FIT, ScaleMethod.AUTOMATIC,
                "{uid}.tiff", FileFormat.TIFF, 100);
        ImageProcessRequest req = new ImageProcessRequest(preset, bucket(), img, img.getName(), "foo");

        // WHEN
        ProcessedImage processedImage = processedImage = processor.process(req);

        // THEN
        assertNotNull(processedImage);
        assertTrue(processedImage.image.exists());

        openImage(processedImage.image.getAbsolutePath());
    }

    @Test
    public void testProcess_PNGtoJPG() throws Exception {
        // GIVEN
        File img = readTestImage("./test/large.png");
        Preset preset = new Preset("thumb", 150, 150, ResizeStrategy.FIT, ScaleMethod.AUTOMATIC,
                "{uid}.jpg", FileFormat.JPG, 100);
        ImageProcessRequest req = new ImageProcessRequest(preset, bucket(), img, img.getName(), "foo");

        // WHEN
        ProcessedImage processedImage = processedImage = processor.process(req);

        // THEN
        assertNotNull(processedImage);
        assertTrue(processedImage.image.exists());

        openImage(processedImage.image.getAbsolutePath());
    }

    @Test
    public void testProcess_PNGAlphaToJPG() throws Exception {
        // GIVEN
        File img = readTestImage("./test/alpha.png");
        Preset preset = new Preset("thumb", 150, 150, ResizeStrategy.FIT, ScaleMethod.AUTOMATIC,
                "{uid}.jpg", FileFormat.JPG, 100);
        ImageProcessRequest req = new ImageProcessRequest(preset, bucket(), img, img.getName(), "foo");

        // WHEN
        ProcessedImage processedImage = processedImage = processor.process(req);

        // THEN
        assertNotNull(processedImage);
        assertTrue(processedImage.image.exists());

        openImage(processedImage.image.getAbsolutePath());
    }

    @Test
    public void testProcess_PNGAlphaToJPG2() throws Exception {
        // GIVEN
        File img = readTestImage("./test/alpha2.png");
        Preset preset = new Preset("thumb", 150, 150, ResizeStrategy.FIT, ScaleMethod.AUTOMATIC, "{uid}.jpg", FileFormat.JPG, 100);
        ImageProcessRequest req = new ImageProcessRequest(preset, bucket(), img, img.getName(), "foo");

        // WHEN
        ProcessedImage processedImage = processedImage = processor.process(req);

        // THEN
        assertNotNull(processedImage);
        assertTrue(processedImage.image.exists());

        openImage(processedImage.image.getAbsolutePath());
    }

    @Test
    public void testProcess_PNGAlphaToPNG() throws Exception {
        // GIVEN
        File img = readTestImage("./test/alpha.png");
        Preset preset = new Preset("thumb", 150, 150, ResizeStrategy.FIT, ScaleMethod.AUTOMATIC, "{uid}.png", FileFormat.PNG, 100);
        ImageProcessRequest req = new ImageProcessRequest(preset, bucket(), img, img.getName(), "foo");

        // WHEN
        ProcessedImage processedImage = processedImage = processor.process(req);

        // THEN
        assertNotNull(processedImage);
        assertTrue(processedImage.image.exists());

        openImage(processedImage.image.getAbsolutePath());
    }


    public void openImage(String absolutePath) throws Exception {
        Runtime r = Runtime.getRuntime();
        Process p = r.exec("open " + absolutePath);
    }

    private File readTestImage(String filename) {
        File f = new File(filename);
        if(!f.exists()) {
            fail("Test image does not seem to exist");
        }
        return f;
    }

    private Bucket bucket() {
        return new Bucket(null, null, null, null, "http://foo.com", false);
    }
}
