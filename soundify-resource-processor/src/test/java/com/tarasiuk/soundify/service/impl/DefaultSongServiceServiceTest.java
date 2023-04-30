package com.tarasiuk.soundify.service.impl;

import com.taraiuk.soundify.data.SongData;
import com.tarasiuk.soundify.client.SongServiceClient;
import com.tarasiuk.soundify.exception.ClientCallException;
import feign.FeignException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultSongServiceServiceTest {

    @Mock
    private SongServiceClient songServiceClient;
    @InjectMocks
    private DefaultSongServiceService testInstance;
    private SongData songData;

    @BeforeEach
    void setUp() {
        songData = new SongData(null, null, "1", null, null, null);
    }

    @Test
    void shouldReturnByteArrayAfterGetAudio() {
        when(songServiceClient.uploadSong(any())).thenReturn(any());

        testInstance.uploadSong(songData);

        verify(songServiceClient).uploadSong(any());
    }

    @Test
    void shouldThrowClientCallExceptionInCaseOfClientIssues() {
        when(songServiceClient.uploadSong(any())).thenThrow(FeignException.class);

        assertThrows(ClientCallException.class, () ->
                testInstance.uploadSong(songData));
    }

}