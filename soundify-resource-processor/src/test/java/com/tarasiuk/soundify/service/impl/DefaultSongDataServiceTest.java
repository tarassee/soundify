package com.tarasiuk.soundify.service.impl;

import com.taraiuk.soundify.data.SongData;
import com.tarasiuk.soundify.exception.MetadataException;
import org.apache.tika.metadata.Metadata;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DefaultSongDataServiceTest {

    private DefaultSongDataService testInstance;
    private Metadata metadata;

    @BeforeEach
    void setUp() {
        testInstance = new DefaultSongDataService();
        metadata = new Metadata();
    }

    @Test
    void shouldRetrieveFromMetadata() {
        metadata.add("dc:title", "Song title");
        metadata.add("xmpDM:artist", "Artist name");
        metadata.add("xmpDM:album", "Album name");
        metadata.add("xmpDM:duration", "150");
        metadata.add("xmpDM:releaseDate", "1922");

        SongData result =
                testInstance.retrieveFrom(123, metadata);

        assertEquals("Song title", result.name());
        assertEquals("Artist name", result.artist());
        assertEquals("Album name", result.album());
        assertEquals("2:30", result.length());
        assertEquals("1922", result.year());
    }

    @Test
    void shouldThrowMetadataExceptionWhenMetadataHasNoRequiredParameters() {
        metadata.add("xmpDM:artist", "Artist name");

        assertThrows(MetadataException.class, () ->
                testInstance.retrieveFrom(123, metadata));
    }

}