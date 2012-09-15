package org.brains3;

import org.codehaus.jackson.annotate.JsonIgnore;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: Joel Soderstrom (joel[at]frostdigital[dot]se)
 * Date: 2012-09-14
 * Time: 1:59 PM
 */
public class ProcessedImage {


    public final String generatedFilename;
    public final String originalFilename;
    public final Preset preset;

    @JsonIgnore
    public File image;
    public String name;
    public String url;
    public Long size;
    public int width;
    public int height;

    public ProcessedImage(Preset preset, String originalFilename) {
        this.preset = preset;
        this.originalFilename = originalFilename;
        this.generatedFilename = preset.filenameGenerator.generate(originalFilename, preset.format);
    }

    /*public String message;
    public List<UploadedFile> files = new ArrayList<UploadedFile>();
    public String fileUrl;*/


}
