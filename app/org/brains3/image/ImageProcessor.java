package org.brains3.image;

import org.brains3.ImageProcessRequest;
import org.brains3.ProcessedImage;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Joel Soderstrom (joel[at]frostdigital[dot]se)
 * Date: 2012-09-09
 * Time: 7:59 PM
 */
public interface ImageProcessor {

    public ProcessedImage process(ImageProcessRequest imageProcessRequest, BufferedImage img) throws IOException;

}
