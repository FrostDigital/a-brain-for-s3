package org.brains3;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: Joel Soderstrom (joel[at]frostdigital[dot]se)
 * Date: 2012-09-14
 * Time: 1:59 PM
 */
public class ProcessedImage {

    public final File image;
    public final Long size;
    public final int width;
    public final int height;

    public ProcessedImage(File image, int width, int height) {
        this.image = image;
        this.size = image != null ? image.length() : -1;
        this.width = width;
        this.height = height;
    }

    /*public String message;
  public List<UploadedFile> files = new ArrayList<UploadedFile>();
  public String fileUrl;*/


}
