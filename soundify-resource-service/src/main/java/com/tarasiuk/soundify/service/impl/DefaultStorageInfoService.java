package com.tarasiuk.soundify.service.impl;

import com.taraiuk.soundify.data.StorageInfoData;
import com.tarasiuk.soundify.client.StorageInfoServiceClient;
import com.tarasiuk.soundify.exception.ClientCallException;
import com.tarasiuk.soundify.model.StorageType;
import com.tarasiuk.soundify.service.StorageInfoService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class DefaultStorageInfoService implements StorageInfoService {

    private final StorageInfoServiceClient storageInfoServiceClient;

    @Override
    public StorageInfoData getStorageInfoByStorageType(StorageType storageType) {
        try {
            return storageInfoServiceClient.getStorageInfoByStorageType(storageType.name());
        } catch (FeignException e) {
            log.error("Failed to get storage info with 'storageType' = '{}' from storage-info-service. Reason: {}",
                    storageType, e.getCause());
            throw new ClientCallException(e.getMessage());
        }
    }

}
