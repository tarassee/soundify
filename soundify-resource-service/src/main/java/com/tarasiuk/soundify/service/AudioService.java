package com.tarasiuk.soundify.service;

import com.tarasiuk.soundify.model.Audio;

import java.util.Optional;

public interface AudioService {

    Audio saveAudio(Audio audio);

    Optional<Audio> findAudioById(Integer id);

    void deleteAudio(Audio audio);

}
