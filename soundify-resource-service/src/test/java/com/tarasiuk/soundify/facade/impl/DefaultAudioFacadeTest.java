package com.tarasiuk.soundify.facade.impl;

import com.taraiuk.soundify.data.ResourceMessageData;
import com.tarasiuk.soundify.exception.AudioNotFoundException;
import com.tarasiuk.soundify.model.Audio;
import com.tarasiuk.soundify.model.StorageType;
import com.tarasiuk.soundify.producer.ResourceMessageProducer;
import com.tarasiuk.soundify.service.AudioService;
import com.tarasiuk.soundify.service.S3StorageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultAudioFacadeTest {

    private static final Integer AUDIO_ID = 1;
    private static final String AUDIO_NAME = "test.mp3";
    private static final String AUDIO_CONTENT_TYPE = "audio/mpeg";
    private static final byte[] AUDIO_CONTENT = new byte[]{1, 2, 3};
    private static final String AUDIO_S3_KEY = "s3_key";
    private static final StorageType STAGING_TYPE = StorageType.STAGING;
    private static final StorageType PERMANENT_TYPE = StorageType.PERMANENT;

    @InjectMocks
    private DefaultAudioFacade testInstance;

    @Mock
    private AudioService audioService;
    @Mock
    private S3StorageService s3StorageService;
    @Mock
    private ResourceMessageProducer resourceMessageProducer;
    @Mock
    private MultipartFile audioFile;
    private Audio testAudio;

    @BeforeEach
    void setUp() {
        testAudio = Audio.builder()
                .id(AUDIO_ID)
                .name(AUDIO_NAME)
                .format(AUDIO_CONTENT_TYPE)
                .s3Key(AUDIO_S3_KEY)
                .storageType(STAGING_TYPE)
                .build();
    }

    @Test
    void shouldSaveToStaging() {
        when(s3StorageService.uploadFile(audioFile, STAGING_TYPE)).thenReturn(AUDIO_S3_KEY);
        when(audioService.saveAudio(any(Audio.class))).thenReturn(testAudio);

        Integer result =
                testInstance.saveToStaging(audioFile);

        assertEquals(AUDIO_ID, result);
        verify(s3StorageService).uploadFile(audioFile, STAGING_TYPE);
        verify(audioService).saveAudio(any(Audio.class));
        verify(resourceMessageProducer).send(any(ResourceMessageData.class));
    }

    @Test
    void shouldMoveToPermanent() {
        when(audioService.findAudioById(AUDIO_ID)).thenReturn(Optional.of(testAudio));
        when(audioService.saveAudio(testAudio)).thenReturn(testAudio);

        Audio result =
                testInstance.moveToPermanent(AUDIO_ID);

        assertEquals(PERMANENT_TYPE, result.getStorageType());
        verify(s3StorageService).moveFile(AUDIO_S3_KEY, STAGING_TYPE, PERMANENT_TYPE);
        verify(audioService).saveAudio(any(Audio.class));
    }

    @Test
    void shouldThrowAudioNotFoundExceptionWhenMovingToPermanentWithNonexistentAudioId() {
        when(audioService.findAudioById(AUDIO_ID)).thenReturn(Optional.empty());

        assertThrows(AudioNotFoundException.class, () ->
                testInstance.moveToPermanent(AUDIO_ID));

        verify(s3StorageService, never()).moveFile(anyString(), any(StorageType.class), any(StorageType.class));
        verify(audioService, never()).saveAudio(any(Audio.class));
    }

    @Test
    void shouldGetAudioContentById() {
        when(audioService.findAudioById(AUDIO_ID)).thenReturn(Optional.of(testAudio));
        when(s3StorageService.downloadFile(AUDIO_S3_KEY, STAGING_TYPE)).thenReturn(AUDIO_CONTENT);

        byte[] result =
                testInstance.getAudioContentById(AUDIO_ID);

        assertArrayEquals(AUDIO_CONTENT, result);
        verify(s3StorageService).downloadFile(AUDIO_S3_KEY, STAGING_TYPE);
    }

    @Test
    void shouldThrowAudioNotFoundExceptionWhenGettingAudioContentByIdWithNonexistentAudioId() {
        when(audioService.findAudioById(AUDIO_ID)).thenReturn(Optional.empty());

        assertThrows(AudioNotFoundException.class, () ->
                testInstance.getAudioContentById(AUDIO_ID));

        verify(s3StorageService, never()).downloadFile(anyString(), any(StorageType.class));
    }

    @Test
    void shouldDeleteAudio() {
        when(audioService.findAudioById(AUDIO_ID)).thenReturn(Optional.of(testAudio));

        Integer result =
                testInstance.deleteAudio(AUDIO_ID);

        assertEquals(AUDIO_ID, result);
        verify(s3StorageService).deleteFile(AUDIO_S3_KEY, STAGING_TYPE);
        verify(audioService).deleteAudio(any(Audio.class));
    }

    @Test
    void shouldReturnNullWhenDeletingNonexistentAudio() {
        when(audioService.findAudioById(AUDIO_ID)).thenReturn(Optional.empty());

        Integer result =
                testInstance.deleteAudio(AUDIO_ID);

        assertNull(result);
        verify(s3StorageService, never()).deleteFile(anyString(), any(StorageType.class));
        verify(audioService, never()).deleteAudio(any(Audio.class));
    }

}
