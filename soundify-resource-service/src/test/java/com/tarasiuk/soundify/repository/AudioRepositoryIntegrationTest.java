package com.tarasiuk.soundify.repository;

import com.tarasiuk.soundify.model.Audio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AudioRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private AudioRepository audioRepository;

    private Audio audio;

    @BeforeEach
    void setUp() {
        audio = Audio.builder()
                .name("test.mp3")
                .format("audio/mpeg")
                .s3Key("s3-test-key")
                .build();
    }

    @Test
    void shouldSaveAudioToDatabase() {
        var savedAudio = audioRepository.save(audio);

        Audio actualAudio = testEntityManager.find(Audio.class, savedAudio.getId());
        assertEquals(savedAudio, actualAudio);
    }

    @Test
    void shouldFindAudioFromDatabaseById() {
        Integer audioId = (Integer) testEntityManager.persistAndGetId(audio);

        Optional<Audio> foundAudio = audioRepository.findById(audioId);

        assertTrue(foundAudio.isPresent());
        assertEquals(foundAudio.get(), audio);
    }

    @Test
    void shouldReturnEmptyOptionalIfAudioNotFoundInDatabase() {
        Integer nonexistentAudioId = 100;

        Optional<Audio> foundAudio = audioRepository.findById(nonexistentAudioId);

        assertTrue(foundAudio.isEmpty());
    }

    @Test
    void shouldDeleteAudioFromDatabase() {
        Integer audioId = (Integer) testEntityManager.persistAndGetId(audio);

        audioRepository.deleteById(audioId);

        assertNull(testEntityManager.find(Audio.class, audioId));
    }

    @Test
    void shouldReturnTrueWhenAudioExistsInDatabase() {
        Integer audioId = (Integer) testEntityManager.persistAndGetId(audio);

        boolean audioExistsResult = audioRepository.existsById(audioId);

        assertTrue(audioExistsResult);
    }

    @Test
    void shouldReturnFalseWhenAudioDoesNotExistInDatabase() {
        int nonexistentAudioId = 100;

        boolean audioExistsResult = audioRepository.existsById(nonexistentAudioId);

        assertFalse(audioExistsResult);
    }

}