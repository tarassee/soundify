package com.tarasiuk.soundify.service.impl;

import com.tarasiuk.soundify.exception.InvalidRequestException;
import com.tarasiuk.soundify.model.Song;
import com.tarasiuk.soundify.repository.SongRepository;
import com.tarasiuk.soundify.service.SongService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class DefaultSongService implements SongService {

    private final SongRepository songRepository;

    @Override
    public Integer uploadSong(Song song) {
        if (songRepository.existsByResourceId(song.getResourceId())) {
            throw new InvalidRequestException("Song with such a resource id is already present!");
        }
        Integer id = songRepository.save(song).getId();
        log.info("SongData with resource id {} was saved to database", id);
        return id;
    }

    @Override
    public Optional<Song> findSong(Integer id) {
        return songRepository.findById(id);
    }

    @Override
    public Optional<Song> findSongByResourceId(Integer resourceId) {
        return songRepository.findByResourceId(resourceId);
    }

    @Override
    public Integer deleteSong(Integer id) {
        songRepository.deleteById(id);
        return id;
    }

    @Override
    public boolean existsById(Integer id) {
        return songRepository.existsById(id);
    }
}
