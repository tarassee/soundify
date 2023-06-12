package com.tarasiuk.soundify.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultS3StorageServiceTest {

    private static final String FILE_NAME = "test.mp3";
    private static final String BUCKET_NAME = "bucketName";
    private static final byte[] audioContent = new byte[]{1, 2, 3};
    @Mock
    private AmazonS3 s3Client;
    @InjectMocks
    @Spy
    private DefaultS3StorageService testInstance;
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
        ReflectionTestUtils.setField(testInstance, "bucketName", BUCKET_NAME);
    }

//    @Test
//    void shouldReturnFileNameAfterUploadFile() {
//        when(audioFile.getOriginalFilename()).thenReturn(FILE_NAME);
//        doReturn(convertedFile).when(testInstance).convertMultiPartFileToFile(audioFile);
//        doNothing().when(testInstance).deleteConvertedFile(any(File.class));
//        when(s3Client.putObject(any(PutObjectRequest.class))).thenReturn(null);
//
//        String result =
//                testInstance.uploadFile(audioFile);
//
//        assertTrue(result.endsWith(FILE_NAME));
//        verify(s3Client).putObject(any(PutObjectRequest.class));
//        verify(testInstance).deleteConvertedFile(convertedFile);
//    }
//
//    @Test
//    void shouldReturnFileContentAfterDownloadFile() throws IOException {
//        when(s3Client.getObject(BUCKET_NAME, FILE_NAME)).thenReturn(s3Object);
//        when(s3Object.getObjectContent()).thenReturn(inputStream);
//        doReturn(audioContent).when(testInstance).toByteArray(inputStream);
//
//        byte[] result =
//                testInstance.downloadFile(FILE_NAME);
//
//        assertArrayEquals(audioContent, result);
//        verify(s3Client).getObject(BUCKET_NAME, FILE_NAME);
//    }
//
//    @Test
//    void shouldReturnFileNameAfterDeleteFile() {
//        String result =
//                testInstance.deleteFile(FILE_NAME);
//
//        assertEquals(FILE_NAME, result);
//        verify(s3Client).deleteObject(BUCKET_NAME, FILE_NAME);
//    }

}
