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
                FilenameGenerator.UID, FileFormat.JPG, 50);

        ProcessedImage processedImage = new ProcessedImage(preset, img.getName());

        // WHEN
        processedImage = processor.process(processedImage, img);

        // THEN
        assertNotNull(processedImage);
        assertTrue(processedImage.image.exists());

        openImage(processedImage.image.getAbsolutePath());
    }

    @Test
    public void testProcess_JPGtoPNG() throws Exception {
        // GIVEN
        File img = readTestImage("./test/large.jpg");
        Preset preset = new Preset("thumb", 150, 150, ResizeStrategy.FIT, ScaleMethod.AUTOMATIC,
                FilenameGenerator.UID, FileFormat.PNG, 100);
        ProcessedImage processedImage = new ProcessedImage(preset, img.getName());

        // WHEN
        processedImage = processor.process(processedImage, img);

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
                FilenameGenerator.UID, FileFormat.BMP, 100);
        ProcessedImage processedImage = new ProcessedImage(preset, img.getName());

        // WHEN
        processedImage = processor.process(processedImage, img);

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
                FilenameGenerator.UID, FileFormat.GIF, 100);
        ProcessedImage processedImage = new ProcessedImage(preset, img.getName());

        // WHEN
        processedImage = processor.process(processedImage, img);

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
                FilenameGenerator.UID, FileFormat.TIFF, 100);
        ProcessedImage processedImage = new ProcessedImage(preset, img.getName());

        // WHEN
        processedImage = processor.process(processedImage, img);

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
                FilenameGenerator.UID, FileFormat.PNG, 100);
        ProcessedImage processedImage = new ProcessedImage(preset, img.getName());

        // WHEN
        processedImage = processor.process(processedImage, img);

        // THEN
        assertNotNull(processedImage);
        assertTrue(processedImage.image.exists());

        openImage(processedImage.image.getAbsolutePath());
    }

    @Test
    public void testProcess_PNGAlphatoJPG() throws Exception {
        // GIVEN
        File img = readTestImage("./test/alpha.png");
        Preset preset = new Preset("thumb", 150, 150, ResizeStrategy.FIT, ScaleMethod.AUTOMATIC,
                FilenameGenerator.UID, FileFormat.PNG, 100);
        ProcessedImage processedImage = new ProcessedImage(preset, img.getName());

        // WHEN
        processedImage = processor.process(processedImage, img);

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
}
