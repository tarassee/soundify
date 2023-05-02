package com.tarasiuk.soundify.service;

import com.tarasiuk.soundify.model.Song;

import java.util.Optional;

public interface SongService {

    Integer uploadSong(Song song);

    Optional<Song> findSong(Integer id);

    Optional<Song> findSongByResourceId(Integer resourceId);

    Integer deleteSong(Integer id);

    boolean existsById(Integer id);

}
