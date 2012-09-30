package org.brains3;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import play.Logger;

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

    public static void uploadFile(ProcessedImage processedImage, ImageProcessRequest imageProcessRequest) throws IOException {
        Long start = System.currentTimeMillis();
        InputStream inputStream = new FileInputStream(processedImage.image);

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(imageProcessRequest.preset.format.contentType);
        objectMetadata.setContentLength(processedImage.image.length());

        imageProcessRequest.bucket.client.putObject(
            new PutObjectRequest(imageProcessRequest.bucket.name, imageProcessRequest.generatedFilename, inputStream, objectMetadata)
        );

        //processedImage.url = bucket.publicUrl + processedImage.generatedFilename;

        inputStream.close();

        Logger.debug("Uploaded " + imageProcessRequest.generatedFilename + " to S3 (" + (System.currentTimeMillis() - start) + " ms)");
    }

}
