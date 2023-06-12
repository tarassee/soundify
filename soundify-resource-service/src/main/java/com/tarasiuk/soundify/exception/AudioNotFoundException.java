package com.tarasiuk.soundify.exception;

public class AudioNotFoundException extends RuntimeException {
    public AudioNotFoundException(Integer id) {
        super(String.format("Audio entry with id %d was not found", id));
    }
}
