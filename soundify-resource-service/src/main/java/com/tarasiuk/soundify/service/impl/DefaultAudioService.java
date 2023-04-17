package com.tarasiuk.soundify.service.impl;

import com.tarasiuk.soundify.model.Audio;
import com.tarasiuk.soundify.producer.ResourceMessageProducer;
import com.tarasiuk.soundify.repository.AudioRepository;
import com.tarasiuk.soundify.service.AudioService;
import com.tarasiuk.soundify.service.S3StorageService;
import data.ResourceMessageData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class DefaultAudioService implements AudioService {

    private final AudioRepository audioRepository;
    private final S3StorageService s3StorageService;
    private final ResourceMessageProducer resourceMessageProducer;

    @Override
    public Integer uploadAudio(MultipartFile audioFile) {
        String audioFileName = s3StorageService.uploadFile(audioFile);
        Audio internalAudio = Audio.builder()
                .name(audioFile.getOriginalFilename())
                .format(audioFile.getContentType())
                .s3Key(audioFileName)
                .build();
        Integer id = audioRepository.save(internalAudio).getId();
        resourceMessageProducer.send(new ResourceMessageData(id));
        return id;
    }

    @Override
    public Optional<byte[]> getAudioContentById(Integer id) {
        Optional<Audio> audio = audioRepository.findById(id);
        return audio.map(value -> s3StorageService.downloadFile(value.getS3Key()));
    }

    @Override
    public Integer deleteAudio(Integer id) {
        Optional<Audio> audio = audioRepository.findById(id);
        if (audio.isPresent()) {
            s3StorageService.deleteFile(audio.get().getS3Key());
            audioRepository.deleteById(id);
            return id;
        }
        return null;
    }

    @Override
    public boolean existsById(Integer id) {
        return audioRepository.existsById(id);
    }
}
