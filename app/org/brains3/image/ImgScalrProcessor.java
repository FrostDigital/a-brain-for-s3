package org.brains3.image;

import org.brains3.Preset;
import org.brains3.ProcessedImage;
import org.imgscalr.Scalr;
import play.Logger;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.imgscalr.Scalr.resize;


/**
 * Created with IntelliJ IDEA.
 * User: Joel Soderstrom (joel[at]frostdigital[dot]se)
 * Date: 2012-09-09
 * Time: 8:02 PM
 */
public class ImgScalrProcessor implements ImageProcessor {

    @Override
    public ProcessedImage process(ProcessedImage processedImage, File image) throws IOException {
        BufferedImage img = null;

        if(isPNG(processedImage.originalFilename)) {
            // Perform PNG -> JPG alpha fix
            // https://github.com/thebuzzmedia/imgscalr/issues/59#issuecomment-3743920

            BufferedImage tmpImg = ImageIO.read(image);

            Logger.warn("PNG file:\n" + tmpImg.toString());
            Logger.warn("PNG file color model:\n" + tmpImg.getColorModel().toString());


            if(tmpImg.getColorModel().hasAlpha()) {
                img = new BufferedImage (tmpImg.getWidth(), tmpImg.getHeight(), BufferedImage.TYPE_INT_ARGB);

                Graphics g = img.getGraphics();

                g.drawImage(tmpImg, 0, 0, null);
                g.dispose();

                img.setData(tmpImg.getData());
                tmpImg.flush();
            }
            else {
                img = tmpImg;
            }

        }
        else {
            img = ImageIO.read(image);
        }

        img = resize(img, getScalrMethod(processedImage.preset), processedImage.preset.width, processedImage.preset.height);

        processedImage.image = write(img, processedImage.preset);
        processedImage.width = img.getWidth();
        processedImage.height = img.getHeight();

        return processedImage;
    }

    private File write(BufferedImage img, Preset preset) {
        File file = null;

        try {
            ImageWriter writer = getImageWriter(preset.format.name());
            ImageWriteParam iwp = setCompressionLevel(writer, preset);

            file = File.createTempFile("brains3_", "." + preset.format.name().toLowerCase());
            FileImageOutputStream output = new FileImageOutputStream(file);

            writer.setOutput(output);
            writer.write(null, new IIOImage(img, null, null), iwp);
            writer.dispose();
            img.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file;
    }

    private ImageWriteParam setCompressionLevel(ImageWriter writer, Preset preset) {
        ImageWriteParam iwp = writer.getDefaultWriteParam();
        try {
            iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            iwp.setCompressionQuality(
                    preset.compressionLevel == null ? 50 : (float) preset.compressionLevel/100
            );
        } catch (UnsupportedOperationException e){
            // Fail quietly if file format does not
            // support compression
        }
        return iwp;
    }

    private ImageWriter getImageWriter(String fileFormat) {
        return ImageIO.getImageWritersByFormatName(fileFormat).next();
    }

    /**
     * Translates ScaleMethod -> Scalr.Method
     * @param preset
     * @return
     */
    private Scalr.Method getScalrMethod(Preset preset) {
        Scalr.Method method = Scalr.Method.AUTOMATIC;

        switch (preset.scaleMethod){
            case SPEED: method = Scalr.Method.SPEED; break;
            case MEDIUM: method = Scalr.Method.BALANCED; break;
            case QUALITY: method = Scalr.Method.QUALITY; break;
            case HIGH_QUALITY: method = Scalr.Method.ULTRA_QUALITY; break;
        }

        return method;
    }

    private boolean isPNG(String filename) {
        return filename.toLowerCase().endsWith(".png");
    }
}
