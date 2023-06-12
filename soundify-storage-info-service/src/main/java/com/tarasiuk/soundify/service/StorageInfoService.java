package com.tarasiuk.soundify.service;

import com.tarasiuk.soundify.model.StorageInfo;

import java.util.List;
import java.util.Optional;

public interface StorageInfoService {

    Integer uploadStorageInfo(StorageInfo storageInfo);

    List<StorageInfo> getAllStorageInfoEntries();

    Optional<StorageInfo> findStorageInfoByStorageType(String storageType);

    Integer deleteStorageInfo(Integer id);

    boolean existsById(Integer id);

}
