package com.tarasiuk.soundify.client;

import com.taraiuk.soundify.data.SongData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@FeignClient(name = "song-service", url = "${application.song-service.url}")
public interface SongServiceClient {

    @Retryable(exceptionExpression = "message.contains('timeout')", maxAttempts = 4)
    @PostMapping("/songs")
    Map<String, Integer> uploadSong(SongData songData);

}
