package org.brains3;

import org.codehaus.jackson.annotate.JsonIgnore;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: Joel Soderstrom (joel[at]frostdigital[dot]se)
 * Date: 2012-09-25
 * Time: 1:41 PM
 */
public class ImageUploadRequest {

    public final String generatedFilename;
    public final String originalFilename;
    public final Preset preset;
    public final String url;
    @JsonIgnore public final Bucket bucket;
    @JsonIgnore public final File file;
    //public Long size;
    //public int width;
    //public int height;

    //@JsonIgnore public final File image;


    public ImageUploadRequest(/*String generatedFilename,*/ Preset preset, Bucket bucket, File file, String originalFilename) {
        this.originalFilename = originalFilename;
        this.generatedFilename = preset.filenameGenerator.generate(originalFilename, preset.format);
        this.preset = preset;
        this.url = bucket.publicUrl + this.generatedFilename;
        this.file = file;
        this.bucket = bucket;
    }
}
