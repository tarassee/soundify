package com.tarasiuk.soundify.fallback;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
public class DefaultFallbackController {

    @RequestMapping("/fallback/resource")
    public ResponseEntity<ErrorResponse> resourceFallback() {
        return createResponse(HttpStatus.SERVICE_UNAVAILABLE,
                "Resource Service is currently unavailable. Please try again later.");
    }

    @RequestMapping("/fallback/song")
    public ResponseEntity<ErrorResponse> songFallback() {
        return createResponse(HttpStatus.SERVICE_UNAVAILABLE,
                "Song Service is currently unavailable. Please try again later.");
    }

    @RequestMapping("/fallback/storages")
    public ResponseEntity<ErrorResponse> storagesFallback() {
        return createResponse(HttpStatus.SERVICE_UNAVAILABLE,
                "Storages Info Service is currently unavailable. Please try again later.");
    }

    private ResponseEntity<ErrorResponse> createResponse(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new ErrorResponse(httpStatus.value(), message, Instant.now().toString()), httpStatus);
    }

    private record ErrorResponse(int status, String message, String timestamp) {
    }

}
