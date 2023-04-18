package com.tarasiuk.soundify.client;

import data.SongData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@FeignClient(name = "song-service", url = "${application.song-service.url}")
public interface SongServiceClient {

    @PostMapping("/songs")
    Map<String, Integer> uploadSong(SongData songData);

}
