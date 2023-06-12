package com.tarasiuk.soundify.controller;

import com.taraiuk.soundify.controller.StorageInfoController;
import com.taraiuk.soundify.data.StorageInfoData;
import com.tarasiuk.soundify.exception.StorageInfoNotFoundException;
import com.tarasiuk.soundify.mapper.StorageInfoMapper;
import com.tarasiuk.soundify.model.StorageInfo;
import com.tarasiuk.soundify.service.StorageInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.tarasiuk.soundify.helper.ControllerHelper.validateAndParseIdsRequestParam;

@RequiredArgsConstructor
@RequestMapping("/storages")
@RestController
public class DefaultStorageInfoController implements StorageInfoController {

    private final StorageInfoService storageInfoService;
    private final StorageInfoMapper storageInfoMapper;

    @PostMapping
    @Override
    public ResponseEntity<Map<String, Integer>> createStorageInfo(@Valid @RequestBody final StorageInfoData storageInfoData) {
        int storageInfoId = storageInfoService.uploadStorageInfo(storageInfoMapper.toStorageInfo(storageInfoData));

        return new ResponseEntity<>(Map.of("id", storageInfoId), HttpStatus.OK);
    }

    @GetMapping
    @Override
    public ResponseEntity<List<StorageInfoData>> getAllStorageInfoEntries() {
        var storageInfoDataList = storageInfoService.getAllStorageInfoEntries().stream()
                .map(storageInfoMapper::toStorageInfoData)
                .toList();
        return new ResponseEntity<>(storageInfoDataList, HttpStatus.OK);
    }

    @GetMapping("/type/{storageType}")
    @Override
    public ResponseEntity<StorageInfoData> getStorageInfoByStorageType(@PathVariable String storageType) {
        Optional<StorageInfo> storageInfo = storageInfoService.findStorageInfoByStorageType(storageType);

        if (storageInfo.isEmpty()) {
            throw new StorageInfoNotFoundException("Storage Info is not found by given storageType: " + storageType);
        }

        return new ResponseEntity<>(storageInfoMapper.toStorageInfoData(storageInfo.get()), HttpStatus.OK);
    }

    @DeleteMapping
    @Override
    public ResponseEntity<Map<String, int[]>> deleteStorageInfo(@RequestParam("ids") String ids) {
        List<Integer> storageInfoIds = validateAndParseIdsRequestParam(ids);

        int[] deletedIds = storageInfoIds.stream()
                .filter(storageInfoService::existsById)
                .mapToInt(storageInfoService::deleteStorageInfo)
                .toArray();
        return new ResponseEntity<>(Map.of("ids", deletedIds), HttpStatus.OK);
    }

}
