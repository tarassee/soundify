package com.tarasiuk.soundify.service;

import com.taraiuk.soundify.data.StorageInfoData;
import com.tarasiuk.soundify.model.StorageType;

public interface StorageInfoService {

    StorageInfoData getStorageInfoByStorageType(StorageType storageType);

}
