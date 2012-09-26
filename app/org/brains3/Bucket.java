package org.brains3;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import play.Logger;
import play.cache.Cache;

/**
 * Created with IntelliJ IDEA.
 * User: Joel Soderstrom (joel[at]frostdigital[dot]se)
 * Date: 2012-09-08
 * Time: 11:01 PM
 */
public class Bucket {

    private static final String BUCKET_CACHE_PREFIX = "bucket.";

    public AmazonS3 client = null;

    public final String alias;
    public final String name;
    public final String key;
    public final String secret;
    public final String publicUrl;

    public Bucket(String alias, String name, String key, String secret, String publicUrl) {
        this(alias, name, key, secret, publicUrl, true);
    }

    public Bucket(String alias, String name, String key, String secret, String publicUrl, boolean initClient) {
        this.alias = alias;
        this.name = name;
        this.key = key;
        this.secret = secret;
        this.publicUrl = publicUrl;

        if(initClient) {
            initS3Client();
        }
    }

    public void initS3Client() {
        AWSCredentials awsCredentials = new BasicAWSCredentials(key, secret);
        client = new AmazonS3Client(awsCredentials);
        testConnection();
    }

    public void testConnection() {
        try {
            if (!client.doesBucketExist(name)) {
                Logger.error("---------- Bucket " + name + " does not exist - you need to create it -----------");
            }
            Logger.info("Successfully connected to bucket: " + name + " (alias: " + alias + ")");
        } catch(AmazonClientException e) {
            Logger.error("Error while accessing S3 bucket " + this.name + ": " + e.getMessage());
        }
    }

    public static Bucket getBucket(final String bucketAlias) {
        return (Bucket) Cache.get(BUCKET_CACHE_PREFIX + bucketAlias);
    }

    public static void saveBucket(Bucket bucket) {
        Logger.debug("Adding bucket: " + bucket.toString());
        Cache.set(BUCKET_CACHE_PREFIX + bucket.alias, bucket);
    }

    public String getMaskedKey() {
        return key != null ? key.substring(0,2) + "*****" : null;
    }

    @Override
    public String toString() {
        return "Bucket{" +
                "client=" + client +
                ", alias='" + alias + '\'' +
                ", name='" + name + '\'' +
                ", key='" + getMaskedKey() + '\'' +
                ", secret='*****" + '\'' +
                ", publicUrl='" + publicUrl + '\'' +
                '}';
    }
}
