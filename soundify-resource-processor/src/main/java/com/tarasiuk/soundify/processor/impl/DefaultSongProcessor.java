package com.tarasiuk.soundify.processor.impl;

import com.tarasiuk.soundify.processor.SongProcessor;
import com.tarasiuk.soundify.service.ResourceServiceService;
import com.tarasiuk.soundify.service.SongDataService;
import com.tarasiuk.soundify.service.SongServiceService;
import data.SongData;
import lombok.RequiredArgsConstructor;
import org.apache.tika.metadata.Metadata;
import org.springframework.stereotype.Component;

import static com.tarasiuk.soundify.util.MetadataExtractorUtil.extractMetadata;

@RequiredArgsConstructor
@Component
public class DefaultSongProcessor implements SongProcessor {

    private final ResourceServiceService resourceServiceService;
    private final SongServiceService songServiceService;
    private final SongDataService songDataService;

    @Override
    public void processSong(Integer id) {
        byte[] audio = resourceServiceService.getAudio(id);

        Metadata metadata = extractMetadata(audio);

        SongData songData = songDataService.retrieveFrom(id, metadata);

        songServiceService.uploadSong(songData);
    }

}
