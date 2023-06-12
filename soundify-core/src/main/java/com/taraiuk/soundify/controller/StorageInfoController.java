package com.taraiuk.soundify.controller;

import com.taraiuk.soundify.data.StorageInfoData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

public interface StorageInfoController {

    ResponseEntity<Map<String, Integer>> createStorageInfo(@Valid @RequestBody final StorageInfoData storageInfoData);

    ResponseEntity<List<StorageInfoData>> getAllStorageInfoEntries();

    ResponseEntity<StorageInfoData> getStorageInfoByStorageType(@PathVariable String storageType);

    ResponseEntity<Map<String, int[]>> deleteStorageInfo(@RequestParam("ids") String ids);

}
