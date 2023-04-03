package com.tarasiuk.soundify.data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public record SongData(
        @Size(min = 1, max = 256)
        String name,
        @Size(min = 1, max = 256)
        String artist,
        @Size(min = 1, max = 256)
        String album,
        @NotEmpty
        @Pattern(regexp = "^\\d{1,2}:\\d{2}$")
        String length,
        @NotEmpty
        @Pattern(regexp = "\\d+")
        String resourceId,
        @NotEmpty
        @Pattern(regexp = "^(19|20)\\d{2}$")
        String year
) {
}
