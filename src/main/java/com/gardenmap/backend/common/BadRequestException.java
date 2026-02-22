package com.gardenmap.backend.common;

/**
 * Thrown when the client sends a malformed request (wrong types/values).
 * Maps to HTTP 400.
 */
public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
