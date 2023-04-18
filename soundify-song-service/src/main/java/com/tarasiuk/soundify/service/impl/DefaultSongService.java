package com.tarasiuk.soundify.service.impl;

import com.tarasiuk.soundify.exception.InvalidRequestException;
import com.tarasiuk.soundify.model.Song;
import com.tarasiuk.soundify.repository.SongRepository;
import com.tarasiuk.soundify.service.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class DefaultSongService implements SongService {

    private final SongRepository songRepository;

    @Override
    public Integer uploadSong(Song song) {
        if (songRepository.existsByResourceId(song.getResourceId())) {
            throw new InvalidRequestException("Song with such a resource id is already present!");
        }
        return songRepository.save(song).getId();
    }

    @Override
    public Optional<Song> findSong(Integer id) {
        return songRepository.findById(id);
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
