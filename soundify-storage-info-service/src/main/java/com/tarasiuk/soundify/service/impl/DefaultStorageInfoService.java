package com.tarasiuk.soundify.service.impl;

import com.tarasiuk.soundify.exception.InvalidRequestException;
import com.tarasiuk.soundify.model.StorageInfo;
import com.tarasiuk.soundify.repository.StorageInfoRepository;
import com.tarasiuk.soundify.service.StorageInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class DefaultStorageInfoService implements StorageInfoService {

    private final StorageInfoRepository storageInfoRepository;

    @Override
    public Integer uploadStorageInfo(StorageInfo storageInfo) {
        if (findStorageInfoByStorageType(storageInfo.getStorageType()).isPresent()) {
            throw new InvalidRequestException("Storage Info entry with such a 'storageType' is already present!");
        }
        return storageInfoRepository.save(storageInfo).getId();
    }

    @Override
    public List<StorageInfo> getAllStorageInfoEntries() {
        return storageInfoRepository.findAll();
    }

    @Override
    public Optional<StorageInfo> findStorageInfoByStorageType(String storageType) {
        return storageInfoRepository.findStorageInfoByStorageType(storageType);
    }

    @Override
    public Integer deleteStorageInfo(Integer id) {
        storageInfoRepository.deleteById(id);
        return id;
    }

    @Override
    public boolean existsById(Integer id) {
        return storageInfoRepository.existsById(id);
    }

}
