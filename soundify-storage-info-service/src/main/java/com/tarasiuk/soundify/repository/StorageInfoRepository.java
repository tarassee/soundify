package com.tarasiuk.soundify.repository;

import com.tarasiuk.soundify.model.StorageInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StorageInfoRepository extends JpaRepository<StorageInfo, Integer> {

    Optional<StorageInfo> findStorageInfoByStorageType(String storageType);

}
