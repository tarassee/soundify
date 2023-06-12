package com.tarasiuk.soundify.service.impl;

import com.taraiuk.soundify.data.ResourceMessageData;
import com.tarasiuk.soundify.model.Audio;
import com.tarasiuk.soundify.model.StorageType;
import com.tarasiuk.soundify.producer.ResourceMessageProducer;
import com.tarasiuk.soundify.repository.AudioRepository;
import com.tarasiuk.soundify.service.AudioService;
import com.tarasiuk.soundify.service.S3StorageService;
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
    public Integer createAudio(MultipartFile audioFile, StorageType storageType) {
        String audioFileName = s3StorageService.uploadFile(audioFile, storageType);

        Audio internalAudio = buildAudio(audioFile, audioFileName, storageType);

        Integer id = audioRepository.save(internalAudio).getId();

        resourceMessageProducer.send(new ResourceMessageData(id));
        return id;
    }

    @Override
    public Audio moveAudio(Audio audio, StorageType destinationStorageType) {
        s3StorageService.moveFile(audio.getS3Key(), audio.getStorageType(), destinationStorageType);
        audio.setStorageType(destinationStorageType);
        return audioRepository.save(audio);
    }

    private static Audio buildAudio(MultipartFile audioFile, String audioFileName, StorageType storageType) {
        return Audio.builder()
                .name(audioFile.getOriginalFilename())
                .format(audioFile.getContentType())
                .s3Key(audioFileName)
                .storageType(storageType)
                .build();
    }

    @Override
    public Optional<Audio> findAudioById(Integer id) {
        return audioRepository.findById(id);
    }

    @Override
    public Optional<byte[]> getAudioContentById(Integer id) {
        Optional<Audio> audio = audioRepository.findById(id);

        return audio.map(value -> s3StorageService.downloadFile(value.getS3Key(), value.getStorageType()));
    }

    @Override
    public Integer deleteAudio(Integer id) {
        Optional<Audio> audio = audioRepository.findById(id);
        return audio.map(audioValue -> {
            s3StorageService.deleteFile(audioValue.getS3Key(), audioValue.getStorageType());
            audioRepository.deleteById(id);
            return id;
        }).orElse(null);
    }

    @Override
    public boolean existsById(Integer id) {
        return audioRepository.existsById(id);
    }
}
