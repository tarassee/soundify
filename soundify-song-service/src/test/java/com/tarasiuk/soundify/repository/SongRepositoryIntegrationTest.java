package com.tarasiuk.soundify.repository;

import com.tarasiuk.soundify.model.Song;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class SongRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired
    private SongRepository songRepository;
    private Song song;

    @BeforeEach
    void setUp() {
        song = Song.builder()
                .name("Name")
                .artist("Artist")
                .album("Album")
                .length("2:34")
                .resourceId(12)
                .year("2001")
                .build();
    }

    @Test
    void shouldSaveSongToDatabase() {
        var savedSong = songRepository.save(song);

        Song actualSong = testEntityManager.find(Song.class, savedSong.getId());
        assertEquals(savedSong, actualSong);
    }

    @Test
    void shouldFindSongFromDatabaseById() {
        Integer songId = (Integer) testEntityManager.persistAndGetId(song);

        Optional<Song> foundSong =
                songRepository.findById(songId);

        assertTrue(foundSong.isPresent());
        assertEquals(foundSong.get(), song);
    }

    @Test
    void shouldReturnEmptyOptionalIfSongNotFoundInDatabase() {
        Integer nonexistentSongId = 100;

        Optional<Song> foundSong =
                songRepository.findById(nonexistentSongId);

        assertTrue(foundSong.isEmpty());
    }

    @Test
    void shouldDeleteSongFromDatabase() {
        Integer songId = (Integer) testEntityManager.persistAndGetId(song);

        songRepository.deleteById(songId);

        assertNull(testEntityManager.find(Song.class, songId));
    }

    @Test
    void shouldReturnTrueWhenSongExistsInDatabase() {
        Integer songId = (Integer) testEntityManager.persistAndGetId(song);

        boolean songExistsResult =
                songRepository.existsById(songId);

        assertTrue(songExistsResult);
    }

    @Test
    void shouldReturnFalseWhenSongExistsInDatabase() {
        int nonexistentSongId = 100;

        boolean songExistsResult =
                songRepository.existsById(nonexistentSongId);

        assertFalse(songExistsResult);
    }

}
