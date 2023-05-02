package com.taraiuk.soundify.controller;

import com.taraiuk.soundify.data.SongData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Map;

public interface SongController {

    ResponseEntity<Map<String, Integer>> uploadSong(@Valid @RequestBody final SongData songData);

    ResponseEntity<SongData> getSong(@PathVariable Integer id);

    ResponseEntity<SongData> getSongByResourceId(@PathVariable Integer resourceId);

    ResponseEntity<Map<String, int[]>> deleteSong(@RequestParam("id") String ids);

}
