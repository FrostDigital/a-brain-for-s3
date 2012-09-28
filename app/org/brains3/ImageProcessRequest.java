package org.brains3;

import org.codehaus.jackson.annotate.JsonIgnore;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: Joel Soderstrom (joel[at]frostdigital[dot]se)
 * Date: 2012-09-25
 * Time: 1:41 PM
 */
public class ImageProcessRequest {

    public final String generatedFilename;
    public final String originalFilename;
    public final Preset preset;
    public final String url;
    @JsonIgnore public final Bucket bucket;
    @JsonIgnore public final File file;

    public ImageProcessRequest(Preset preset, Bucket bucket, File file, String originalFilename, String generatedFilename) {
        this.originalFilename = originalFilename;
        this.generatedFilename = generatedFilename;
        this.preset = preset;
        this.url = bucket.publicUrl + this.generatedFilename;
        this.file = file;
        this.bucket = bucket;
    }

    public String getBucket() {
        return this.bucket.name;
    }
}
