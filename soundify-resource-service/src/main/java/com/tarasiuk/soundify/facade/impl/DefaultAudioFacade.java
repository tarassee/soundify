package com.tarasiuk.soundify.facade.impl;

import com.taraiuk.soundify.data.ResourceMessageData;
import com.tarasiuk.soundify.exception.AudioNotFoundException;
import com.tarasiuk.soundify.facade.AudioFacade;
import com.tarasiuk.soundify.model.Audio;
import com.tarasiuk.soundify.model.StorageType;
import com.tarasiuk.soundify.producer.ResourceMessageProducer;
import com.tarasiuk.soundify.service.AudioService;
import com.tarasiuk.soundify.service.S3StorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@Service
public class DefaultAudioFacade implements AudioFacade {

    private final AudioService audioService;
    private final S3StorageService s3StorageService;
    private final ResourceMessageProducer resourceMessageProducer;

    @Override
    public Integer saveToStaging(MultipartFile audioFile) {
        StorageType stagingType = StorageType.STAGING;
        String audioFileName = s3StorageService.uploadFile(audioFile, stagingType);
        log.info("Audio {} was uploaded to s3 bucket", audioFileName);

        Audio internalAudio = buildAudio(audioFile, audioFileName, stagingType);
        Integer id = audioService.saveAudio(internalAudio).getId();
        log.info("Audio information was saved to database - id {}", id);

        resourceMessageProducer.send(new ResourceMessageData(id));
        log.info("The message with resource id {} was sent to message queue", id);
        return id;
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
    public Audio moveToPermanent(Integer id) {
        return audioService.findAudioById(id)
                .map(audio -> {
                    StorageType permanentType = StorageType.PERMANENT;
                    s3StorageService.moveFile(audio.getS3Key(), audio.getStorageType(), permanentType);
                    audio.setStorageType(permanentType);
                    Audio savedAudio = audioService.saveAudio(audio);
                    log.info("Audio with id {} was moved to permanent storage", id);
                    return savedAudio;
                })
                .orElseThrow(() -> new AudioNotFoundException(id));
    }

    @Override
    public byte[] getAudioContentById(Integer id) {
        return audioService.findAudioById(id)
                .map(audio -> s3StorageService.downloadFile(audio.getS3Key(), audio.getStorageType()))
                .orElseThrow(() -> new AudioNotFoundException(id));
    }

    @Override
    public Integer deleteAudio(Integer id) {
        return audioService.findAudioById(id)
                .map(audio -> {
                    s3StorageService.deleteFile(audio.getS3Key(), audio.getStorageType());
                    audioService.deleteAudio(audio);
                    return audio.getId();
                }).orElse(null);
    }

}
