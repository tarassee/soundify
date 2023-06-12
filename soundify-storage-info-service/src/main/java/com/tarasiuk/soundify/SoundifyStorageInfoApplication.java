package com.tarasiuk.soundify;

import com.tarasiuk.soundify.model.StorageInfo;
import com.tarasiuk.soundify.repository.StorageInfoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@EnableEurekaClient
@SpringBootApplication
public class SoundifyStorageInfoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SoundifyStorageInfoApplication.class, args);
    }

    // TODO: move data initialization from this place
    @Bean
    public CommandLineRunner initStorageInfo(StorageInfoRepository storageInfoRepository) {
        return args -> {
            if (storageInfoRepository.count() == 0) {
                saveStorageInfo(storageInfoRepository, "STAGING", "soundify-staged-storage-bucket", "/");
                saveStorageInfo(storageInfoRepository, "PERMANENT", "soundify-permanent-storage-bucket", "/");
            }
        };
    }

    private void saveStorageInfo(StorageInfoRepository storageInfoRepository, String storageType, String bucket, String path) {
        storageInfoRepository.save(StorageInfo.builder()
                .storageType(storageType)
                .bucket(bucket)
                .path(path)
                .build());
    }

}
