package com.tarasiuk.soundify.client;

import com.taraiuk.soundify.data.StorageInfoData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "${application.storage-info-service.name}")
public interface StorageInfoServiceClient {

    @GetMapping("storages/type/{storageType}")
    StorageInfoData getStorageInfoByStorageType(@PathVariable("storageType") String storageType);

}
