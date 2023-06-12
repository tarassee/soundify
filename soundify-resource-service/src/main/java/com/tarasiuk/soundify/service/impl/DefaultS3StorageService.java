package com.tarasiuk.soundify.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.taraiuk.soundify.util.FilesUtil;
import com.tarasiuk.soundify.model.StorageType;
import com.tarasiuk.soundify.service.S3StorageService;
import com.tarasiuk.soundify.service.StorageInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Service
public class DefaultS3StorageService implements S3StorageService {

    private final AmazonS3 s3Client;
    private final StorageInfoService storageInfoService;

    @Override
    public String uploadFile(MultipartFile file, StorageType storageType) {
        String bucketName = getBucketName(storageType);
        File convertedFile = convertMultiPartFileToFile(file);
        String fileName = getUniqueFileName(file);

        try {
            s3Client.putObject(new PutObjectRequest(bucketName, fileName, convertedFile));
            return fileName;
        } finally {
            deleteConvertedFile(convertedFile);
        }
    }

    private static String getUniqueFileName(MultipartFile file) {
        return UUID.randomUUID() + "_" + file.getOriginalFilename();
    }

    @Override
    public byte[] downloadFile(String fileName, StorageType storageType) {
        String bucketName = getBucketName(storageType);
        S3Object s3Object = s3Client.getObject(bucketName, fileName);

        try (S3ObjectInputStream inputStream = s3Object.getObjectContent()) {
            return toByteArray(inputStream);
        } catch (IOException e) {
            log.error("Error while retrieving S3 file '{}' from bucket '{}': {}", fileName, bucketName, e.getMessage());
            return new byte[0];
        }
    }

    @Override
    public String deleteFile(String fileName, StorageType storageType) {
        String bucketName = getBucketName(storageType);
        s3Client.deleteObject(bucketName, fileName);
        return fileName;
    }

    @Override
    public void moveFile(String s3Key, StorageType sourceStorageType, StorageType destinationStorageType) {
        String sourceBucketName = getBucketName(sourceStorageType);
        String destinationBucketName = getBucketName(destinationStorageType);

        s3Client.copyObject(sourceBucketName, s3Key, destinationBucketName, s3Key);
        deleteFile(s3Key, sourceStorageType);
    }

    private String getBucketName(StorageType storageType) {
        return storageInfoService.getStorageInfoByStorageType(storageType).bucket();
    }

    protected File convertMultiPartFileToFile(MultipartFile file) {
        return FilesUtil.convertMultiPartFileToFile(file);
    }

    protected void deleteConvertedFile(File file) {
        FilesUtil.deleteFile(file);
    }

    protected byte[] toByteArray(S3ObjectInputStream inputStream) throws IOException {
        return IOUtils.toByteArray(inputStream);
    }

}
