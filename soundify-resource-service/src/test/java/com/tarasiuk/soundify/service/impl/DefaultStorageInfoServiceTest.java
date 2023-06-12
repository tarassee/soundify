package com.tarasiuk.soundify.service.impl;

import com.taraiuk.soundify.data.StorageInfoData;
import com.tarasiuk.soundify.client.StorageInfoServiceClient;
import com.tarasiuk.soundify.exception.ClientCallException;
import com.tarasiuk.soundify.model.StorageType;
import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultStorageInfoServiceTest {

    private static final StorageType STORAGE_TYPE = StorageType.STAGING;
    private static final StorageInfoData STORAGE_INFO_DATA = new StorageInfoData("test", "test", "/");

    @InjectMocks
    private DefaultStorageInfoService testInstance;

    @Mock
    private StorageInfoServiceClient storageInfoServiceClient;

    @Test
    void shouldReturnStorageInfoDataForStorageType() {
        when(storageInfoServiceClient.getStorageInfoByStorageType(STORAGE_TYPE.name())).thenReturn(STORAGE_INFO_DATA);

        var result =
                testInstance.getStorageInfoByStorageType(STORAGE_TYPE);

        assertEquals(STORAGE_INFO_DATA, result);
        verify(storageInfoServiceClient).getStorageInfoByStorageType(STORAGE_TYPE.name());
    }

    @Test
    void shouldThrowClientCallExceptionInCaseOfClientIssues() {
        when(storageInfoServiceClient.getStorageInfoByStorageType(any())).thenThrow(FeignException.class);

        assertThrows(ClientCallException.class, () ->
                testInstance.getStorageInfoByStorageType(STORAGE_TYPE));
    }

}
