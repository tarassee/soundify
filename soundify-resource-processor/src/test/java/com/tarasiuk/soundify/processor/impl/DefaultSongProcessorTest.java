package com.tarasiuk.soundify.processor.impl;

import com.taraiuk.soundify.data.SongData;
import com.tarasiuk.soundify.exception.ClientCallException;
import com.tarasiuk.soundify.exception.MetadataException;
import com.tarasiuk.soundify.service.ResourceServiceService;
import com.tarasiuk.soundify.service.SongDataService;
import com.tarasiuk.soundify.service.SongServiceService;
import org.apache.tika.metadata.Metadata;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultSongProcessorTest {

    private static final Integer ID = 123;
    private static final byte[] AUDIO_BYTES = new byte[0];
    @Mock
    private ResourceServiceService resourceServiceService;
    @Mock
    private SongServiceService songServiceService;
    @Mock
    private SongDataService songDataService;
    @InjectMocks
    @Spy
    private DefaultSongProcessor testInstance;
    @Mock
    private Metadata metadata;
    private SongData songData;

    @BeforeEach
    void setUp() {
        when(resourceServiceService.getAudio(ID)).thenReturn(AUDIO_BYTES);
        when(testInstance.extractMetadata(AUDIO_BYTES)).thenReturn(metadata);
        when(songDataService.retrieveFrom(ID, metadata)).thenReturn(songData);
    }

    @Test
    void shouldProcessSongSuccessfully() {
        testInstance.processSong(ID);

        verify(resourceServiceService).getAudio(ID);
        verify(testInstance).extractMetadata(AUDIO_BYTES);
        verify(songDataService).retrieveFrom(ID, metadata);
        verify(songServiceService).uploadSong(songData);
    }

    @Test
    void shouldThrowMetadataExceptionIfMetadataNotValid() {
        when(songDataService.retrieveFrom(ID, metadata)).thenThrow(new MetadataException());

        assertThrows(MetadataException.class, () ->
                testInstance.processSong(ID));

        verify(resourceServiceService).getAudio(ID);
        verify(testInstance).extractMetadata(AUDIO_BYTES);
        verify(songDataService).retrieveFrom(ID, metadata);
        verifyNoInteractions(songServiceService);
    }

    @Test
    void shouldThrowClientCallExceptionIfSongServiceClientThrowsException() {
        doThrow(ClientCallException.class).when(songServiceService).uploadSong(songData);

        assertThrows(ClientCallException.class, () ->
                testInstance.processSong(ID));
    }
}
