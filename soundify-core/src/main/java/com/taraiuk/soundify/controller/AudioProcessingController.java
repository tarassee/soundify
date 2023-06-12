package com.taraiuk.soundify.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface AudioProcessingController {

    ResponseEntity<Map<String, Integer>> uploadAudio(@RequestPart("audio") MultipartFile audio);

    ResponseEntity<Void> updateAudioStorage(@PathVariable Integer id);

    ResponseEntity<byte[]> getAudio(@PathVariable Integer id,
                                    @RequestHeader(value = "Range", required = false) String rangeHeader);

    ResponseEntity<Map<String, Integer[]>> deleteAudio(@RequestParam("id") String ids);

}
