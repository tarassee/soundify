package com.tarasiuk.soundify.facade;

import com.tarasiuk.soundify.model.Audio;
import org.springframework.web.multipart.MultipartFile;

public interface AudioFacade {

    Integer saveToStaging(MultipartFile audioFile);

    Audio moveToPermanent(Integer id);

    byte[] getAudioContentById(Integer id);

    Integer deleteAudio(Integer id);

}
