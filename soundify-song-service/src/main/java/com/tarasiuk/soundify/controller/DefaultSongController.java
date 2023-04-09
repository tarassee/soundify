package com.tarasiuk.soundify.controller;

import com.tarasiuk.soundify.exception.SongNotFoundException;
import com.tarasiuk.soundify.mapper.SongMapper;
import com.tarasiuk.soundify.model.Song;
import com.tarasiuk.soundify.service.SongService;
import controller.SongController;
import data.SongData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.tarasiuk.soundify.helper.ControllerHelper.validateAndParseIdsRequestParam;

@RequiredArgsConstructor
@RequestMapping("songs")
@RestController
public class DefaultSongController implements SongController {

    private final SongService songService;
    private final SongMapper songMapper;

    @Override
    @PostMapping
    public ResponseEntity<Map<String, Integer>> uploadSong(@Valid @RequestBody final SongData songData) {
        int songId = songService.uploadSong(songMapper.toSong(songData));

        return new ResponseEntity<>(Map.of("id", songId), HttpStatus.OK);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<SongData> getSong(@PathVariable Integer id) {
        Optional<Song> song = songService.findSong(id);

        if (song.isEmpty()) {
            throw new SongNotFoundException("Song is not found by given ID: " + id);
        }

        return new ResponseEntity<>(songMapper.toSongData(song.get()), HttpStatus.OK);
    }

    @Override
    @DeleteMapping
    public ResponseEntity<Map<String, int[]>> deleteSong(@RequestParam("id") String ids) {
        List<Integer> songIds = validateAndParseIdsRequestParam(ids);

        int[] deletedIds = songIds.stream()
                .filter(songService::existsById)
                .mapToInt(songService::deleteSong)
                .toArray();
        return new ResponseEntity<>(Map.of("ids", deletedIds), HttpStatus.OK);
    }

}
