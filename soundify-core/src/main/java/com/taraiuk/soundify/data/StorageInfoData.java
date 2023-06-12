package com.taraiuk.soundify.data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public record StorageInfoData(
        @NotEmpty
        @Size(min = 1, max = 256)
        String storageType,
        @NotEmpty
        @Size(min = 1, max = 256)
        String bucket,
        @NotEmpty
        @Size(min = 1, max = 256)
        String path
) {
}
