package org.brains3;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * User: Joel Soderstrom (joel[at]frostdigital[dot]se)
 * Date: 2012-09-14
 * Time: 1:28 PM
 */
public class S3Client {

    public static void uploadFile(Bucket bucket, ProcessedImage processedImage) throws IOException {
        InputStream inputStream = new FileInputStream(processedImage.image);

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(processedImage.preset.format.contentType);
        objectMetadata.setContentLength(processedImage.image.length());

        bucket.client.putObject(
            new PutObjectRequest(bucket.name, processedImage.generatedFilename, inputStream, objectMetadata)
        );

        processedImage.url = bucket.publicUrl + processedImage.generatedFilename;

        inputStream.close();
    }

}
