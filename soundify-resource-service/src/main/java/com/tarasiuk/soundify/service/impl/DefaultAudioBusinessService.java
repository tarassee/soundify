package com.tarasiuk.soundify.service.impl;

import com.tarasiuk.soundify.model.Audio;
import com.tarasiuk.soundify.model.StorageType;
import com.tarasiuk.soundify.service.AudioBusinessService;
import com.tarasiuk.soundify.service.AudioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class DefaultAudioBusinessService implements AudioBusinessService {

    private final AudioService audioService;

    @Override
    public Integer saveToStaging(MultipartFile audioFile) {
        return audioService.createAudio(audioFile, StorageType.STAGING);
    }

    @Override
    public Audio moveToPermanent(Integer id) {
        return audioService.findAudioById(id)
                .map(value -> audioService.moveAudio(value, StorageType.PERMANENT))
                .orElse(null);
    }

}
