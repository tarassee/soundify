package com.taraiuk.soundify.data;

import org.springframework.lang.Nullable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public record SongData(
        @Size(min = 1, max = 256)
        String name,
        @Size(min = 1, max = 256)
        String artist,
        @NotEmpty
        @Pattern(regexp = "\\d+")
        String resourceId,
        @Size(min = 1, max = 256)
        @Nullable
        String album,
        @Pattern(regexp = "^\\d{1,2}:\\d{2}$")
        @Nullable
        String length,
        @Pattern(regexp = "^(19|20)\\d{2}$")
        @Nullable
        String year
) {
}
