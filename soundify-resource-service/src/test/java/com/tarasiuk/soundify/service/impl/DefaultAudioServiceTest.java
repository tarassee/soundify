package com.tarasiuk.soundify.service.impl;

import com.tarasiuk.soundify.model.Audio;
import com.tarasiuk.soundify.repository.AudioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultAudioServiceTest {

    private static final int AUDIO_ID = 1;

    @InjectMocks
    private DefaultAudioService testInstance;

    @Mock
    private AudioRepository audioRepository;
    @Mock
    private Audio audio;

    @Test
    void shouldSaveAudio() {
        when(audioRepository.save(audio)).thenReturn(audio);

        var savedAudio =
                testInstance.saveAudio(audio);

        verify(audioRepository).save(audio);
        assertEquals(audio, savedAudio);
    }

    @Test
    void shouldFindAudioById() {
        when(audioRepository.findById(AUDIO_ID)).thenReturn(Optional.of(audio));

        var savedAudio =
                testInstance.findAudioById(AUDIO_ID);

        verify(audioRepository).findById(AUDIO_ID);
        assertTrue(savedAudio.isPresent());
        assertEquals(audio, savedAudio.get());
    }

    @Test
    void shouldDeleteAudioEntity() {
        doNothing().when(audioRepository).delete(audio);

        testInstance.deleteAudio(audio);

        verify(audioRepository).delete(audio);
    }

}
