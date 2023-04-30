package com.tarasiuk.soundify.service.impl;

import com.tarasiuk.soundify.exception.InvalidRequestException;
import com.tarasiuk.soundify.model.Song;
import com.tarasiuk.soundify.repository.SongRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultSongServiceTest {

    @Mock
    private SongRepository songRepository;
    @InjectMocks
    private DefaultSongService testInstance;
    private Song song;

    @BeforeEach
    void setUp() {
        song = new Song();
        song.setId(4);
        song.setResourceId(24);
    }

    @Test
    void shouldReturnIdAfterUploadSongExecution() {
        when(songRepository.existsByResourceId(anyInt())).thenReturn(false);
        when(songRepository.save(any(Song.class))).thenReturn(song);

        Integer id =
                testInstance.uploadSong(song);

        verify(songRepository).save(song);
        assertNotNull(id);
        assertEquals(song.getId(), id);
    }

    @Test
    void shouldThrowExceptionIfResourceIdIsNotUnique() {
        when(songRepository.existsByResourceId(anyInt())).thenReturn(true);

        assertThrows(InvalidRequestException.class, () ->
                testInstance.uploadSong(song));
    }

    @Test
    void shouldReturnOptionalSongIfSongWasFound() {
        when(songRepository.findById(anyInt())).thenReturn(Optional.of(song));

        Optional<Song> songOptional =
                testInstance.findSong(song.getId());

        assertTrue(songOptional.isPresent());
        assertEquals(song, songOptional.get());
    }

    @Test
    void shouldReturnOptionalEmptyIfSongWasNotFound() {
        when(songRepository.findById(anyInt())).thenReturn(Optional.empty());

        Optional<Song> songOptional =
                testInstance.findSong(song.getId());

        assertFalse(songOptional.isPresent());
    }

    @Test
    void shouldReturnSongIdAfterDeleteSongExecution() {
        doNothing().when(songRepository).deleteById(anyInt());

        Integer id =
                testInstance.deleteSong(song.getId());

        verify(songRepository).deleteById(song.getId());
        assertNotNull(id);
        assertEquals(song.getId(), id);
    }

    @Test
    void shouldReturnTrueIfSongExistsById() {
        when(songRepository.existsById(anyInt())).thenReturn(true);

        boolean result =
                testInstance.existsById(song.getId());

        verify(songRepository).existsById(song.getId());
        assertTrue(result);
    }

}
