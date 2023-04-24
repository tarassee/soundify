package com.tarasiuk.soundify.controller;

import com.tarasiuk.soundify.exception.AudioNotFoundException;
import com.tarasiuk.soundify.exception.InvalidRequestException;
import com.tarasiuk.soundify.service.AudioService;
import com.taraiuk.soundify.controller.AudioProcessingController;
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
import java.util.Optional;

import static com.tarasiuk.soundify.helper.ControllerHelper.*;

@RequiredArgsConstructor
@RequestMapping("/resources")
@RestController
public class DefaultAudioProcessingController implements AudioProcessingController {

    private static final String MP3_FORMAT = "audio/mpeg";
    private final AudioService audioService;

    @Override
    @PostMapping
    public ResponseEntity<Map<String, Integer>> uploadAudio(@RequestPart("audio") MultipartFile audio) {

        if (!Objects.equals(audio.getContentType(), MP3_FORMAT)) {
            throw new InvalidRequestException("Given file format is not supported: " + audio.getContentType());
        }

        return new ResponseEntity<>(Map.of("id", audioService.uploadAudio(audio)), HttpStatus.OK);
    }

    @Override
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> getAudio(@PathVariable Integer id,
                                           @RequestHeader(value = "Range", required = false) String rangeHeader) {
        Optional<byte[]> audio = audioService.getAudioContentById(id);

        if (audio.isEmpty()) {
            throw new AudioNotFoundException("Audio file is not found by given ID: " + id);
        }

        if (StringUtils.hasText(rangeHeader)) {
            int[] range = validateAndParseRangeHeader(rangeHeader);
            return new ResponseEntity<>(getByteRange(audio.get(), range[0], range[1]), HttpStatus.PARTIAL_CONTENT);
        }

        return new ResponseEntity<>(audio.get(), HttpStatus.OK);
    }

    @Override
    @DeleteMapping
    public ResponseEntity<Map<String, int[]>> deleteAudio(@RequestParam("id") String ids) {
        List<Integer> audioIds = validateAndParseIdsRequestParam(ids);

        int[] deletedIds = audioIds.stream()
                .filter(audioService::existsById)
                .mapToInt(audioService::deleteAudio)
                .toArray();
        return new ResponseEntity<>(Map.of("ids", deletedIds), HttpStatus.OK);
    }
}
