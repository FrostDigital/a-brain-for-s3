package org.brains3.image;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.jpeg.JpegDirectory;
import play.Logger;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: Joel Soderstrom (joel[at]frostdigital[dot]se)
 * Date: 2012-10-09
 * Time: 4:09 PM
 */
public class ExifUtil {

    public static Integer readImageOrientation(File imageFile) {
        Metadata metadata = null;
        try {
            metadata = ImageMetadataReader.readMetadata(imageFile);
        }
        catch(Exception e) {
            // Any exception here is handled as if image
            // format doesn't support EXIF
            return null;
        }


        Directory directory = metadata.getDirectory(ExifIFD0Directory.class);
        JpegDirectory jpegDirectory = (JpegDirectory)metadata.getDirectory(JpegDirectory.class);

        int orientation = 1;

        try {
            orientation = directory.getInt(ExifIFD0Directory.TAG_ORIENTATION);
            /*int width = jpegDirectory.getImageWidth();
            int height = jpegDirectory.getImageHeight();*/
        } catch (Exception e) {
            Logger.warn("Could not get orientation");
        }


        return orientation;
    }


}
