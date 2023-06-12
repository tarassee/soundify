package com.tarasiuk.soundify.controller;

import com.taraiuk.soundify.controller.AudioProcessingController;
import com.tarasiuk.soundify.exception.InvalidRequestException;
import com.tarasiuk.soundify.facade.AudioFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.tarasiuk.soundify.helper.ControllerHelper.*;

@RequiredArgsConstructor
@RequestMapping("/resources")
@RestController
public class DefaultAudioProcessingController implements AudioProcessingController {

    private static final String MP3_FORMAT = "audio/mpeg";
    private final AudioFacade audioFacade;

    @Override
    @PostMapping
    public ResponseEntity<Map<String, Integer>> uploadAudio(@RequestPart("audio") MultipartFile audio) {

        if (!Objects.equals(audio.getContentType(), MP3_FORMAT)) {
            throw new InvalidRequestException("Given file format is not supported: " + audio.getContentType());
        }

        return new ResponseEntity<>(Map.of("id", audioFacade.saveToStaging(audio)), HttpStatus.OK);
    }

    @Override
    @PostMapping("/move-to-permanent/{id}")
    public ResponseEntity<Void> updateAudioStorage(@PathVariable Integer id) {
        audioFacade.moveToPermanent(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> getAudio(@PathVariable Integer id,
                                           @RequestHeader(value = "Range", required = false) String rangeHeader) {
        byte[] audio = audioFacade.getAudioContentById(id);

        if (StringUtils.hasText(rangeHeader)) {
            int[] range = validateAndParseRangeHeader(rangeHeader);
            return new ResponseEntity<>(getByteRange(audio, range[0], range[1]), HttpStatus.PARTIAL_CONTENT);
        }

        return new ResponseEntity<>(audio, HttpStatus.OK);
    }

    @Override
    @DeleteMapping
    public ResponseEntity<Map<String, Integer[]>> deleteAudio(@RequestParam("id") String ids) {
        List<Integer> audioIds = validateAndParseIdsRequestParam(ids);

        Integer[] deletedIds = audioIds.stream()
                .map(audioFacade::deleteAudio)
                .filter(Objects::nonNull)
                .toArray(Integer[]::new);
        return new ResponseEntity<>(Map.of("ids", deletedIds), HttpStatus.OK);
    }

}
