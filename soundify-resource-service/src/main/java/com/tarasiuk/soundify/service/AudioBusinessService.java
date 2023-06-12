package com.tarasiuk.soundify.service;

import com.tarasiuk.soundify.model.Audio;
import org.springframework.web.multipart.MultipartFile;

public interface AudioBusinessService {

    Integer saveToStaging(MultipartFile audioFile);

    Audio moveToPermanent(Integer id);

}
