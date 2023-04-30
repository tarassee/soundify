package com.tarasiuk.soundify.controller;

import com.taraiuk.soundify.data.SongData;
import com.tarasiuk.soundify.exception.SongNotFoundException;
import com.tarasiuk.soundify.mapper.SongMapper;
import com.tarasiuk.soundify.model.Song;
import com.tarasiuk.soundify.service.SongService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultSongControllerTest {

    @Mock
    private SongService songService;
    @Mock
    private SongMapper songMapper;
    @InjectMocks
    private DefaultSongController testInstance;
    private SongData songData;
    private Song song;

    @BeforeEach
    public void setUp() {
        song = new Song();
        song.setId(1);
        song.setResourceId(100);
        songData = new SongData("name", "artist", "3245", "album", "3:12", "2021");
    }

    @Test
    void shouldReturnSongIdAfterUploadSongExecution() {
        when(songMapper.toSong(any(SongData.class))).thenReturn(song);
        when(songService.uploadSong(any(Song.class))).thenReturn(song.getId());

        ResponseEntity<Map<String, Integer>> responseEntity =
                testInstance.uploadSong(songData);

        verify(songService).uploadSong(song);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(Map.of("id", song.getId()), responseEntity.getBody());
    }

    @Test
    void shouldReturnSongDataIfSongWasFound() {
        when(songService.findSong(anyInt())).thenReturn(Optional.of(song));
        when(songMapper.toSongData(any(Song.class))).thenReturn(songData);

        ResponseEntity<SongData> responseEntity =
                testInstance.getSong(song.getId());

        verify(songService).findSong(song.getId());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(songData, responseEntity.getBody());
    }

    @Test
    void shouldThrowExceptionIfSongWasNotFound() {
        when(songService.findSong(anyInt())).thenReturn(Optional.empty());
        Integer id = song.getId();

        assertThrows(SongNotFoundException.class, () ->
                testInstance.getSong(id));

        verify(songService).findSong(song.getId());
    }

    @Test
    void shouldReturnDeletedSongIds() {
        when(songService.existsById(anyInt())).thenReturn(true);
        when(songService.deleteSong(anyInt())).thenReturn(song.getId());

        ResponseEntity<Map<String, int[]>> responseEntity =
                testInstance.deleteSong(String.valueOf(song.getId()));

        verify(songService).existsById(song.getId());
        verify(songService).deleteSong(song.getId());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertArrayEquals(new int[]{song.getId()}, responseEntity.getBody().get("ids"));
    }

    @Test
    void shouldReturnEmptyDeletedSongIdsIfAllGivenIdsAreNotValid() {
        when(songService.existsById(anyInt())).thenReturn(false);

        ResponseEntity<Map<String, int[]>> responseEntity =
                testInstance.deleteSong("1,2,3");

        verify(songService, times(3)).existsById(anyInt());
        verify(songService, never()).deleteSong(anyInt());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertArrayEquals(new int[0], responseEntity.getBody().get("ids"));
    }

}
