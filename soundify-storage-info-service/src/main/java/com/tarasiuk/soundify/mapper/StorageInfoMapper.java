package com.tarasiuk.soundify.mapper;

import com.taraiuk.soundify.data.StorageInfoData;
import com.tarasiuk.soundify.model.StorageInfo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StorageInfoMapper {

    StorageInfo toStorageInfo(StorageInfoData storageInfoData);

    StorageInfoData toStorageInfoData(StorageInfo storageInfo);

}
