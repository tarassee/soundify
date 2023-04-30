package com.tarasiuk.soundify.service.impl;

import com.taraiuk.soundify.data.SongData;
import com.tarasiuk.soundify.exception.MetadataException;
import com.tarasiuk.soundify.service.SongDataService;
import org.apache.tika.metadata.Metadata;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
public class DefaultSongDataService implements SongDataService {

    private static final String TITLE = "dc:title";
    private static final String ARTIST = "xmpDM:artist";
    private static final String ALBUM = "xmpDM:album";
    private static final String DURATION = "xmpDM:duration";
    private static final String RELEASE_DATE = "xmpDM:releaseDate";
    private static final String DURATION_FORMAT = "%d:%02d";

    @Override
    public SongData retrieveFrom(Integer id, Metadata metadata) {
        if (!hasRequiredParameters(metadata)) {
            throw new MetadataException("Metadata has not required parameters");
        }

        String name = metadata.get(TITLE);
        String artist = metadata.get(ARTIST);
        String album = metadata.get(ALBUM);
        String length = format(metadata.get(DURATION));
        String year = metadata.get(RELEASE_DATE);

        return new SongData(name, artist, id.toString(), album, length, year);
    }

    private boolean hasRequiredParameters(Metadata metadata) {
        return nonNull(metadata.get(TITLE)) && nonNull(metadata.get(ARTIST));
    }

    private String format(String duration) {
        if (isNull(duration)) {
            return null;
        }
        double seconds = Double.parseDouble(duration);
        long minutes = (long) seconds / 60;
        long remainingSeconds = (long) seconds % 60;
        return String.format(DURATION_FORMAT, minutes, remainingSeconds);
    }

}
