package com.tarasiuk.soundify.service.impl;

import com.tarasiuk.soundify.model.Audio;
import com.tarasiuk.soundify.repository.AudioRepository;
import com.tarasiuk.soundify.service.AudioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class DefaultAudioService implements AudioService {

    private final AudioRepository audioRepository;

    @Override
    public Audio saveAudio(Audio audio) {
        return audioRepository.save(audio);
    }

    @Override
    public Optional<Audio> findAudioById(Integer id) {
        return audioRepository.findById(id);
    }

    @Override
    public void deleteAudio(Audio audio) {
        audioRepository.delete(audio);
    }

}
