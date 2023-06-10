package com.tarasiuk.soundify.config.provider;

import com.amazonaws.services.s3.AmazonS3;

public interface S3ClientProvider {

    AmazonS3 createS3Client();

}
