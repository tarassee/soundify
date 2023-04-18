package com.tarasiuk.soundify.service.impl;

import com.tarasiuk.soundify.client.SongServiceClient;
import com.tarasiuk.soundify.exception.ClientCallException;
import com.tarasiuk.soundify.service.SongServiceService;
import data.SongData;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class DefaultSongServiceService implements SongServiceService {

    private final SongServiceClient songServiceClient;

    @Override
    public void uploadSong(SongData songData) {
        try {
            songServiceClient.uploadSong(songData);
        } catch (FeignException e) {
            log.error("Failed to upload song data with resource id {} to song-service. Reason: {}",
                    songData.resourceId(), e.getCause().toString());
            throw new ClientCallException(e.getMessage());
        }
    }

}
