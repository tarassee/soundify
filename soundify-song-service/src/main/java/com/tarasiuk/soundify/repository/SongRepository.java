package com.tarasiuk.soundify.repository;

import com.tarasiuk.soundify.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SongRepository extends JpaRepository<Song, Integer> {

    boolean existsByResourceId(Integer resourceId);
    Optional<Song> findByResourceId(Integer resourceId);

}
