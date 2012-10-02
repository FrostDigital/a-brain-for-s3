package org.brains3.image;

import org.brains3.*;
import org.junit.Ignore;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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
        BufferedImage img = readTestImage("./test/large.jpg");
        Preset preset = new Preset("thumb", 150, 150, ResizeStrategy.FIT, ScaleMethod.AUTOMATIC,
                "{uid}.jpg", FileFormat.JPG, 50);

        ImageProcessRequest req = new ImageProcessRequest(
                preset, bucket(), null, "large.jpg", "foo");

        // WHEN
        ProcessedImage processedImage = processor.process(req, img);

        // THEN
        assertNotNull(processedImage);
        assertTrue(processedImage.image.exists());

        openImage(processedImage.image.getAbsolutePath());
    }

    @Test
    public void testProcess_JPGtoJPG_fillCrop() throws Exception {
        // GIVEN
        BufferedImage img = readTestImage("./test/large.jpg");
        Preset preset = new Preset("thumb", 150, 150, ResizeStrategy.FILLCROP, ScaleMethod.AUTOMATIC,
                "{uid}.jpg", FileFormat.JPG, 50);

        ImageProcessRequest req = new ImageProcessRequest(
                preset, bucket(), null, "large.jpg", "foo");

        // WHEN
        ProcessedImage processedImage = processor.process(req, img);

        // THEN
        assertNotNull(processedImage);
        assertTrue(processedImage.image.exists());

        openImage(processedImage.image.getAbsolutePath());
    }

    @Test
    public void testProcess_JPGtoPNG() throws Exception {
        // GIVEN
        BufferedImage img = readTestImage("./test/large.jpg");
        Preset preset = new Preset("thumb", 150, 150, ResizeStrategy.FIT, ScaleMethod.QUALITY,
                "{uid}.png", FileFormat.PNG, 100);
        ImageProcessRequest req = new ImageProcessRequest(preset, bucket(), null, "large.jpg", "foo");

        // WHEN
        ProcessedImage processedImage = processedImage = processor.process(req, img);

        // THEN
        assertNotNull(processedImage);
        assertTrue(processedImage.image.exists());

        openImage(processedImage.image.getAbsolutePath());
    }

    @Test
    @Ignore
    public void testProcess_JPGtoBMP() throws Exception {
        // GIVEN
        BufferedImage img = readTestImage("./test/large.jpg");
        Preset preset = new Preset("thumb", 150, 150, ResizeStrategy.FIT, ScaleMethod.AUTOMATIC,
                "{uid}.bmp", FileFormat.BMP, 100);
        ImageProcessRequest req = new ImageProcessRequest(
                preset, bucket(), null, "large.jpg", "foo");


        // WHEN
        ProcessedImage processedImage = processedImage = processor.process(req, img);

        // THEN
        assertNotNull(processedImage);
        assertTrue(processedImage.image.exists());

        openImage(processedImage.image.getAbsolutePath());
    }

    @Test
    @Ignore
    public void testProcess_JPGtoGIF() throws Exception {
        // GIVEN
        BufferedImage img = readTestImage("./test/large.jpg");
        Preset preset = new Preset("thumb", 150, 150, ResizeStrategy.FIT, ScaleMethod.AUTOMATIC,
                "{uid}.gif", FileFormat.GIF, 100);
        ImageProcessRequest req = new ImageProcessRequest(
                preset, bucket(), null, "large.jpg", "foo");

        // WHEN
        ProcessedImage processedImage = processedImage = processor.process(req, img);

        // THEN
        assertNotNull(processedImage);
        assertTrue(processedImage.image.exists());

        openImage(processedImage.image.getAbsolutePath());
    }

    @Test
    @Ignore
    public void testProcess_JPGtoTIF() throws Exception {
        // GIVEN
        BufferedImage img = readTestImage("./test/large.jpg");
        Preset preset = new Preset("thumb", 150, 150, ResizeStrategy.FIT, ScaleMethod.AUTOMATIC,
                "{uid}.tiff", FileFormat.TIFF, 100);
        ImageProcessRequest req = new ImageProcessRequest(
                preset, bucket(), null, "large.jpg", "foo");

        // WHEN
        ProcessedImage processedImage = processedImage = processor.process(req, img);

        // THEN
        assertNotNull(processedImage);
        assertTrue(processedImage.image.exists());

        openImage(processedImage.image.getAbsolutePath());
    }

    @Test
    public void testProcess_PNGtoJPG() throws Exception {
        // GIVEN
        BufferedImage img = readTestImage("./test/large.png");
        Preset preset = new Preset("thumb", 150, 150, ResizeStrategy.FIT, ScaleMethod.AUTOMATIC,
                "{uid}.jpg", FileFormat.JPG, 100);
        ImageProcessRequest req = new ImageProcessRequest(
                preset, bucket(), null, "large.jpg", "foo");

        // WHEN
        ProcessedImage processedImage = processedImage = processor.process(req, img);

        // THEN
        assertNotNull(processedImage);
        assertTrue(processedImage.image.exists());

        openImage(processedImage.image.getAbsolutePath());
    }

    @Test
    public void testProcess_PNGAlphaToJPG() throws Exception {
        // GIVEN
        BufferedImage img = readTestImage("./test/alpha.png");
        Preset preset = new Preset("thumb", 150, 150, ResizeStrategy.FIT, ScaleMethod.AUTOMATIC,
                "{uid}.jpg", FileFormat.JPG, 100);
        ImageProcessRequest req = new ImageProcessRequest(
                preset, bucket(), null, "large.jpg", "foo");

        // WHEN
        ProcessedImage processedImage = processedImage = processor.process(req, img);

        // THEN
        assertNotNull(processedImage);
        assertTrue(processedImage.image.exists());

        openImage(processedImage.image.getAbsolutePath());
    }

    @Test
    public void testProcess_PNGAlphaToJPG2() throws Exception {
        // GIVEN
        BufferedImage img = readTestImage("./test/alpha2.png");
        Preset preset = new Preset("thumb", 150, 150, ResizeStrategy.FIT, ScaleMethod.AUTOMATIC, "{uid}.jpg", FileFormat.JPG, 100);
        ImageProcessRequest req = new ImageProcessRequest(
                preset, bucket(), null, "large.jpg", "foo");

        // WHEN
        ProcessedImage processedImage = processedImage = processor.process(req, img);

        // THEN
        assertNotNull(processedImage);
        assertTrue(processedImage.image.exists());

        openImage(processedImage.image.getAbsolutePath());
    }

    @Test
         public void testProcess_PNGAlphaToPNG() throws Exception {
        // GIVEN
        BufferedImage img = readTestImage("./test/alpha.png");
        Preset preset = new Preset("thumb", 150, 150, ResizeStrategy.FIT, ScaleMethod.AUTOMATIC, "{uid}.png", FileFormat.PNG, 100);
        ImageProcessRequest req = new ImageProcessRequest(
                preset, bucket(), null, "large.jpg", "foo");

        // WHEN
        ProcessedImage processedImage = processedImage = processor.process(req, img);

        // THEN
        assertNotNull(processedImage);
        assertTrue(processedImage.image.exists());

        openImage(processedImage.image.getAbsolutePath());
    }

    @Test
    public void testProcess_fitToHeight() throws Exception {
        // GIVEN
        BufferedImage img = readTestImage("./test/alpha.png");
        Preset preset = new Preset("thumb", 10, 150, ResizeStrategy.FIT_TO_HEIGHT, ScaleMethod.AUTOMATIC, "{uid}.png", FileFormat.PNG, 100);
        ImageProcessRequest req = new ImageProcessRequest(
                preset, bucket(), null, "large.jpg", "foo");

        // WHEN
        ProcessedImage processedImage = processedImage = processor.process(req, img);

        // THEN
        assertNotNull(processedImage);
        assertTrue(processedImage.image.exists());
        assertWidthAndHeight(processedImage.image, null, 150);
    }

    @Test
    public void testProcess_fitToWidth() throws Exception {
        // GIVEN
        BufferedImage img = readTestImage("./test/alpha.png");
        Preset preset = new Preset("thumb", 10, 150, ResizeStrategy.FIT_TO_WIDTH, ScaleMethod.AUTOMATIC, "{uid}.png", FileFormat.PNG, 100);
        ImageProcessRequest req = new ImageProcessRequest(
                preset, bucket(), null, "large.jpg", "foo");

        // WHEN
        ProcessedImage processedImage = processedImage = processor.process(req, img);

        // THEN
        assertNotNull(processedImage);
        assertTrue(processedImage.image.exists());
        assertWidthAndHeight(processedImage.image, 10, null);
    }

    @Test
    public void testProcess_centerCrop() throws Exception {
        // GIVEN
        BufferedImage img = readTestImage("./test/large.jpg");
        Preset preset = new Preset("thumb", 220, 165, ResizeStrategy.CENTER_CROP, ScaleMethod.AUTOMATIC, "{uid}.png", FileFormat.PNG, 100);
        ImageProcessRequest req = new ImageProcessRequest(preset, bucket(), null, "large.jpg", "foo");

        // WHEN
        ProcessedImage processedImage = processedImage = processor.process(req, img);

        // THEN
        assertNotNull(processedImage);
        assertTrue(processedImage.image.exists());
        assertWidthAndHeight(processedImage.image, 220, 165);

        openImage(processedImage.image.getAbsolutePath());
    }


    private static void assertWidthAndHeight(File file, Integer width, Integer height) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(file);
        } catch (IOException e) {
            fail("could not read file ");
        }

        if(width != null) {
            assertEquals(width.intValue(), img.getWidth());
        }

        if(height != null) {
            assertEquals(height.intValue(), img.getHeight());
        }
    }

    public void openImage(String absolutePath) throws Exception {
        Runtime r = Runtime.getRuntime();
        Process p = r.exec("open " + absolutePath);
    }

    private BufferedImage readTestImage(String filename) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(filename));
        } catch (IOException e) {
            fail("Test image does not seem to exist");
        }
        return img;
    }

    private Bucket bucket() {
        return new Bucket(null, null, null, null, "http://foo.com", false);
    }
}
