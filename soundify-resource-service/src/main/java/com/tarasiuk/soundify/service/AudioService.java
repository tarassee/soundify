package com.tarasiuk.soundify.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface AudioService {

    Integer uploadAudio(MultipartFile audioFile);

    Optional<byte[]> getAudioContentById(Integer id);

    Integer deleteAudio(Integer id);

    boolean existsById(Integer id);

}
