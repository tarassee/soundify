package com.tarasiuk.soundify.exception;

public class MetadataException extends RuntimeException {
    public MetadataException() {
        this("Metadata manipulation errors!");
    }
    public MetadataException(String message) {
        super(message);
    }
}
