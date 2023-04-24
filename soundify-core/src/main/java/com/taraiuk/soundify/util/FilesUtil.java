package com.taraiuk.soundify.util;

import com.taraiuk.soundify.exception.FileProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

public class FilesUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(FilesUtil.class);

    private FilesUtil() {
    }

    public static File convertMultiPartFileToFile(MultipartFile file) {
        File convertedFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            LOGGER.error("MultipartFile to File converting error for multipart file: {}", file.getName());
            throw new FileProcessingException(e);
        }
        return convertedFile;
    }

    public static void deleteFile(File file) {
        try {
            Files.delete(file.toPath());
        } catch (IOException e) {
            LOGGER.error("Error while deleting file: {}", file.getName());
            throw new FileProcessingException(e);
        }
    }
}
