package com.tarasiuk.soundify.service;

import com.tarasiuk.soundify.model.Audio;
import com.tarasiuk.soundify.model.StorageType;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface AudioService {

    Integer createAudio(MultipartFile audioFile, StorageType storageType);

    Audio moveAudio(Audio audio, StorageType storageType);

    Optional<Audio> findAudioById(Integer id);

    Optional<byte[]> getAudioContentById(Integer id);

    Integer deleteAudio(Integer id);

    boolean existsById(Integer id);

}
