package io.liliac.shortener.exception;

public class MappingNotFoundException extends RuntimeException {

    public MappingNotFoundException(String message) {
        super(message);
    }
}
