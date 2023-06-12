package com.tarasiuk.soundify.service;

import com.tarasiuk.soundify.model.StorageType;
import org.springframework.web.multipart.MultipartFile;

public interface S3StorageService {
    String uploadFile(MultipartFile file, StorageType storageType);

    byte[] downloadFile(String fileName, StorageType storageType);

    String deleteFile(String fileName, StorageType storageType);

    void moveFile(String s3Key, StorageType sourceStorageType, StorageType destinationStorageType);
}
