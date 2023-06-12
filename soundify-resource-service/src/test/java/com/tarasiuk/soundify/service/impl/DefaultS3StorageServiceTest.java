package com.tarasiuk.soundify.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.taraiuk.soundify.data.StorageInfoData;
import com.tarasiuk.soundify.model.StorageType;
import com.tarasiuk.soundify.service.StorageInfoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultS3StorageServiceTest {

    private static final String FILE_NAME = "test.mp3";
    private static final byte[] AUDIO_CONTENT = new byte[]{1, 2, 3};
    private static final StorageType STAGING_TYPE = StorageType.STAGING;
    private static final StorageType PERMANENT_TYPE = StorageType.PERMANENT;
    private static final String STAGED_BUCKET_NAME = "stagedBucketName";
    private static final String PERMANENT_BUCKET_NAME = "permanentBucket";
    private static final StorageInfoData STAGED_STORAGE_INFO = new StorageInfoData("test1", STAGED_BUCKET_NAME, "/");
    private static final StorageInfoData PERMANENT_STORAGE_INFO = new StorageInfoData("test2", PERMANENT_BUCKET_NAME, "/");

    @InjectMocks
    @Spy
    private DefaultS3StorageService testInstance;

    @Mock
    private AmazonS3 s3Client;
    @Mock
    private StorageInfoService storageInfoService;
    @Mock
    private MultipartFile audioFile;
    @Mock
    private File convertedFile;
    @Mock
    private S3Object s3Object;
    @Mock
    private S3ObjectInputStream inputStream;

    @BeforeEach
    void setUp() {
        lenient().when(storageInfoService.getStorageInfoByStorageType(any(StorageType.class))).thenReturn(STAGED_STORAGE_INFO);
    }

    @Test
    void shouldReturnFileNameAfterUploadFile() {
        doReturn(convertedFile).when(testInstance).convertMultiPartFileToFile(audioFile);
        when(audioFile.getOriginalFilename()).thenReturn(FILE_NAME);
        when(s3Client.putObject(any(PutObjectRequest.class))).thenReturn(null);
        doNothing().when(testInstance).deleteConvertedFile(any(File.class));

        String result =
                testInstance.uploadFile(audioFile, STAGING_TYPE);

        assertTrue(result.endsWith(FILE_NAME));
        verify(s3Client).putObject(any(PutObjectRequest.class));
        verify(testInstance).deleteConvertedFile(convertedFile);
    }

    @Test
    void shouldReturnFileContentAfterDownloadFile() throws IOException {
        when(s3Client.getObject(STAGED_BUCKET_NAME, FILE_NAME)).thenReturn(s3Object);
        when(s3Object.getObjectContent()).thenReturn(inputStream);
        doReturn(AUDIO_CONTENT).when(testInstance).toByteArray(inputStream);

        byte[] result =
                testInstance.downloadFile(FILE_NAME, STAGING_TYPE);

        assertArrayEquals(AUDIO_CONTENT, result);
        verify(s3Client).getObject(STAGED_BUCKET_NAME, FILE_NAME);
    }

    @Test
    void shouldReturnFileNameAfterDeleteFile() {
        String result =
                testInstance.deleteFile(FILE_NAME, STAGING_TYPE);

        assertEquals(FILE_NAME, result);
        verify(s3Client).deleteObject(STAGED_BUCKET_NAME, FILE_NAME);
    }

    @Test
    void shouldMoveFileFromStagingToPermanent() {
        String s3Key = UUID.randomUUID().toString();
        when(s3Client.copyObject(STAGED_BUCKET_NAME, s3Key, PERMANENT_BUCKET_NAME, s3Key)).thenReturn(null);
        when(storageInfoService.getStorageInfoByStorageType(any(StorageType.class)))
                .thenReturn(STAGED_STORAGE_INFO)
                .thenReturn(PERMANENT_STORAGE_INFO)
                .thenReturn(STAGED_STORAGE_INFO);

        testInstance.moveFile(s3Key, STAGING_TYPE, PERMANENT_TYPE);

        verify(s3Client).copyObject(STAGED_BUCKET_NAME, s3Key, PERMANENT_BUCKET_NAME, s3Key);
        verify(s3Client).deleteObject(STAGED_BUCKET_NAME, s3Key);
    }

}
