package com.tarasiuk.soundify.controller;

import com.tarasiuk.soundify.exception.InvalidRequestException;
import com.tarasiuk.soundify.facade.AudioFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultAudioProcessingControllerTest {

    private static final String MP3_FORMAT = "audio/mpeg";
    private static final int AUDIO_ID = 1;
    private static final byte[] AUDIO_CONTENT = new byte[]{1, 2, 3};

    @InjectMocks
    private DefaultAudioProcessingController testInstance;

    @Mock
    private AudioFacade audioFacade;
    private MultipartFile audio;

    @BeforeEach
    void setUp() {
        audio = new MockMultipartFile("audio.mp3", "audio.mp3", MP3_FORMAT, AUDIO_CONTENT);
    }

    @Test
    void shouldReturnAudioIdAfterUploadAudioExecution() {
        when(audioFacade.saveToStaging(audio)).thenReturn(AUDIO_ID);

        ResponseEntity<Map<String, Integer>> responseEntity =
                testInstance.uploadAudio(audio);

        verify(audioFacade).saveToStaging(audio);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(Map.of("id", AUDIO_ID), responseEntity.getBody());
    }

    @Test
    void shouldThrowExceptionIfContentTypeIsNotSupported() {
        MultipartFile unsupportedAudio = new MockMultipartFile("audio.ogg", "audio.ogg", "audio/ogg", AUDIO_CONTENT);

        assertThrows(InvalidRequestException.class, () ->
                testInstance.uploadAudio(unsupportedAudio));

        verify(audioFacade, never()).saveToStaging(any());
    }

    @Test
    void shouldReturnHttpStatusOkIfUpdateAudioStorageExecutionSucceeds() {
        ResponseEntity<Void> responseEntity =
                testInstance.updateAudioStorage(AUDIO_ID);

        verify(audioFacade).moveToPermanent(AUDIO_ID);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void shouldReturnAudioContentIfAudioWasFound() {
        when(audioFacade.getAudioContentById(AUDIO_ID)).thenReturn(AUDIO_CONTENT);

        ResponseEntity<byte[]> responseEntity =
                testInstance.getAudio(AUDIO_ID, null);

        verify(audioFacade).getAudioContentById(AUDIO_ID);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertArrayEquals(AUDIO_CONTENT, responseEntity.getBody());
    }

    @Test
    void shouldReturnPartialContentIfRangeHeaderIsPresent() {
        String rangeHeader = "0-1";
        when(audioFacade.getAudioContentById(AUDIO_ID)).thenReturn(AUDIO_CONTENT);

        ResponseEntity<byte[]> responseEntity =
                testInstance.getAudio(AUDIO_ID, rangeHeader);

        verify(audioFacade).getAudioContentById(AUDIO_ID);
        assertEquals(HttpStatus.PARTIAL_CONTENT, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertArrayEquals(Arrays.copyOfRange(AUDIO_CONTENT, 0, 1), responseEntity.getBody());
    }

    @Test
    void shouldReturnDeletedAudioIdsAfterDeleteAudioExecution() {
        String ids = "1,2,3";
        Integer[] deletedAudioIds = {1, 3};
        when(audioFacade.deleteAudio(anyInt()))
                .thenReturn(1)
                .thenReturn(null)
                .thenReturn(3);

        ResponseEntity<Map<String, Integer[]>> responseEntity =
                testInstance.deleteAudio(ids);

        verify(audioFacade, times(3)).deleteAudio(anyInt());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertArrayEquals(deletedAudioIds, responseEntity.getBody().get("ids"));
    }

}
