package com.web.service.domain.exception;

public class ListEmptyException extends RuntimeException {
    public ListEmptyException(String message) {
        super(message);
    }
}
