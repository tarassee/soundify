package com.tarasiuk.soundify.config.provider.impl;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.tarasiuk.soundify.config.provider.S3ClientProvider;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LocalstackS3ClientProvider implements S3ClientProvider {

    private static final String DUMMY_VALUE = "dummy_value";
    private final String localstackEndpoint;
    private final String region;

    @Override
    public AmazonS3 createS3Client() {
        AWSCredentials credentials = new BasicAWSCredentials(DUMMY_VALUE, DUMMY_VALUE);
        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withPathStyleAccessEnabled(true)
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(localstackEndpoint, region))
                .withClientConfiguration(getClientConfiguration())
                .build();
    }

    private static ClientConfiguration getClientConfiguration() {
        ClientConfiguration configuration = new ClientConfiguration();
        configuration.setMaxErrorRetry(3);
        configuration.setConnectionTimeout(50 * 1000);
        configuration.setSocketTimeout(50 * 1000);
        configuration.setProtocol(Protocol.HTTP);
        return configuration;
    }

}
