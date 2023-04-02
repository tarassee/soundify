package com.tarasiuk.soundify.exception;

public class AudioNotFoundException extends RuntimeException {
    public AudioNotFoundException(String message) {
        super(message);
    }
}
