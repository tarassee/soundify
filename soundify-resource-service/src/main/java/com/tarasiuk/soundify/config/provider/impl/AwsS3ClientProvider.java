package com.tarasiuk.soundify.config.provider.impl;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.tarasiuk.soundify.config.provider.S3ClientProvider;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AwsS3ClientProvider implements S3ClientProvider {

    private final String accessKey;
    private final String accessSecret;
    private final String region;

    @Override
    public AmazonS3 createS3Client() {
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, accessSecret);
        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(region)
                .build();
    }

}
