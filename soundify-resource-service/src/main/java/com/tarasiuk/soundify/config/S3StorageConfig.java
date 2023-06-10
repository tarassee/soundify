package com.tarasiuk.soundify.config;

import com.amazonaws.services.s3.AmazonS3;
import com.tarasiuk.soundify.config.provider.S3ClientProvider;
import com.tarasiuk.soundify.config.provider.impl.AwsS3ClientProvider;
import com.tarasiuk.soundify.config.provider.impl.LocalstackS3ClientProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3StorageConfig {

    @Value("${storage.aws.credentials.access-key}")
    private String accessKey;
    @Value("${storage.aws.credentials.secret-key}")
    private String accessSecret;
    @Value("${storage.aws.region.static}")
    private String region;
    @Value("${storage.localstack.endpoint}")
    private String localstackEndpoint;

    @Bean
    @ConditionalOnProperty(name = "application.storage.type", havingValue = "AWS", matchIfMissing = true)
    public S3ClientProvider awsS3ClientStrategy() {
        return new AwsS3ClientProvider(accessKey, accessSecret, region);
    }

    @Bean
    @ConditionalOnProperty(name = "application.storage.type", havingValue = "LOCALSTACK")
    public S3ClientProvider localstackS3ClientStrategy() {
        return new LocalstackS3ClientProvider(localstackEndpoint, region);
    }

    @Bean
    public AmazonS3 s3Client(S3ClientProvider s3ClientProvider) {
        return s3ClientProvider.createS3Client();
    }

}
