package com.tarasiuk.soundify.service.impl;

import com.taraiuk.soundify.data.ResourceMessageData;
import com.tarasiuk.soundify.model.Audio;
import com.tarasiuk.soundify.producer.ResourceMessageProducer;
import com.tarasiuk.soundify.repository.AudioRepository;
import com.tarasiuk.soundify.service.S3StorageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultAudioServiceTest {

    private static final byte[] audioContent = new byte[]{1, 2, 3};
    @Mock
    private AudioRepository audioRepository;
    @Mock
    private S3StorageService s3StorageService;
    @Mock
    private ResourceMessageProducer resourceMessageProducer;
    @InjectMocks
    private DefaultAudioService testInstance;
    private MockMultipartFile audioFile;
    private Audio audio;

    @BeforeEach
    void setUp() {
        audioFile = new MockMultipartFile("audio.mp3", new byte[]{1, 2, 3});
        audio = Audio.builder().id(1).name("audio.mp3").format("audio/mp3").s3Key("filename").build();
    }

//    @Test
//    void shouldUploadAudioAndReturnId() {
//        doNothing().when(resourceMessageProducer).send(any(ResourceMessageData.class));
//        when(audioRepository.save(any(Audio.class))).thenReturn(audio);
//        when(s3StorageService.uploadFile(any(MultipartFile.class))).thenReturn("filename");
//
//        Integer id =
//                testInstance.uploadAudio(audioFile);
//
//        assertNotNull(id);
//        verify(s3StorageService).uploadFile(audioFile);
//        verify(audioRepository).save(any(Audio.class));
//        verify(resourceMessageProducer).send(any(ResourceMessageData.class));
//    }
//
//    @Test
//    void shouldReturnAudioContentById() {
//        when(s3StorageService.downloadFile(any(String.class))).thenReturn(audioContent);
//        when(audioRepository.findById(any(Integer.class))).thenReturn(Optional.of(audio));
//
//        Optional<byte[]> resultAudioContent =
//                testInstance.getAudioContentById(1);
//
//        assertTrue(resultAudioContent.isPresent());
//        assertArrayEquals(audioContent, resultAudioContent.get());
//    }
//
//    @Test
//    void shouldDeleteAudioAndReturnId() {
//        when(audioRepository.findById(any(Integer.class))).thenReturn(Optional.of(audio));
//
//        Integer id =
//                testInstance.deleteAudio(1);
//
//        assertNotNull(id);
//        verify(s3StorageService).deleteFile("filename");
//        verify(audioRepository).deleteById(1);
//    }

    @Test
    void shouldReturnTrueIfAudioExistsById() {
        when(audioRepository.existsById(any(Integer.class))).thenReturn(true);

        boolean result =
                testInstance.existsById(1);

        assertTrue(result);
        verify(audioRepository).existsById(1);
    }

    @Test
    void shouldReturnFalseIfAudioDoesNotExistById() {
        when(audioRepository.existsById(any(Integer.class))).thenReturn(false);

        boolean result =
                testInstance.existsById(1);

        assertFalse(result);
        verify(audioRepository).existsById(1);
    }

}