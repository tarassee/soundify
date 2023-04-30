package com.tarasiuk.soundify.controller;

import com.tarasiuk.soundify.exception.AudioNotFoundException;
import com.tarasiuk.soundify.exception.InvalidRequestException;
import com.tarasiuk.soundify.service.AudioService;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultAudioProcessingControllerTest {

    private static final String MP3_FORMAT = "audio/mpeg";
    private static final int AUDIO_ID = 1;
    private static final byte[] AUDIO_CONTENT = new byte[] {1, 2, 3};
    @Mock
    private AudioService audioService;
    @InjectMocks
    private DefaultAudioProcessingController testInstance;
    private MultipartFile audio;

    @BeforeEach
    void setUp() {
        audio = new MockMultipartFile("audio.mp3", "audio.mp3", MP3_FORMAT, AUDIO_CONTENT);
    }

    @Test
    void shouldReturnAudioIdAfterUploadAudioExecution() {
        when(audioService.uploadAudio(audio)).thenReturn(AUDIO_ID);

        ResponseEntity<Map<String, Integer>> responseEntity =
                testInstance.uploadAudio(audio);

        verify(audioService).uploadAudio(audio);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(Map.of("id", AUDIO_ID), responseEntity.getBody());
    }

    @Test
    void shouldThrowExceptionIfContentTypeIsNotSupported() {
        MultipartFile unsupportedAudio = new MockMultipartFile("audio.ogg", "audio.ogg", "audio/ogg", AUDIO_CONTENT);

        assertThrows(InvalidRequestException.class, () ->
                testInstance.uploadAudio(unsupportedAudio));

        verify(audioService, never()).uploadAudio(any());
    }

    @Test
    void shouldReturnAudioContentIfAudioWasFound() {
        when(audioService.getAudioContentById(AUDIO_ID)).thenReturn(Optional.of(AUDIO_CONTENT));

        ResponseEntity<byte[]> responseEntity =
                testInstance.getAudio(AUDIO_ID, null);

        verify(audioService).getAudioContentById(AUDIO_ID);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertArrayEquals(AUDIO_CONTENT, responseEntity.getBody());
    }

    @Test
    void shouldReturnPartialContentIfRangeHeaderIsPresent() {
        String rangeHeader = "0-1";
        when(audioService.getAudioContentById(AUDIO_ID)).thenReturn(Optional.of(AUDIO_CONTENT));

        ResponseEntity<byte[]> responseEntity =
                testInstance.getAudio(AUDIO_ID, rangeHeader);

        verify(audioService).getAudioContentById(AUDIO_ID);
        assertEquals(HttpStatus.PARTIAL_CONTENT, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertArrayEquals(Arrays.copyOfRange(AUDIO_CONTENT, 0, 1), responseEntity.getBody());
    }

    @Test
    void shouldThrowExceptionIfAudioWasNotFound() {
        when(audioService.getAudioContentById(AUDIO_ID)).thenReturn(Optional.empty());

        assertThrows(AudioNotFoundException.class, () ->
                testInstance.getAudio(AUDIO_ID, null));

        verify(audioService).getAudioContentById(AUDIO_ID);
    }
}
