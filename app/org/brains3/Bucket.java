package org.brains3;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import play.Logger;
import play.cache.Cache;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Joel Soderstrom (joel[at]frostdigital[dot]se)
 * Date: 2012-09-08
 * Time: 11:01 PM
 */
public class Bucket {

    private static final String CACHE_KEY = "buckets";

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
        Map<String, Bucket> buckets = (Map<String, Bucket>) Cache.get(CACHE_KEY);
        return buckets != null ? buckets.get(bucketAlias) : null;
    }

    public static void saveBucket(Bucket bucket) {
        Map<String, Bucket> buckets = (Map<String, Bucket>) Cache.get(CACHE_KEY);
        if(buckets == null) {
            buckets = new HashMap<String, Bucket>();
            Cache.set(CACHE_KEY, buckets);
        }

        buckets.put(bucket.alias, bucket);
    }

    public static List<Bucket> getAll() {
        Map<String, Bucket> buckets = (Map<String, Bucket>) Cache.get(CACHE_KEY);
        return buckets != null ? new ArrayList<Bucket>(buckets.values()) : Collections.<Bucket>emptyList();
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
