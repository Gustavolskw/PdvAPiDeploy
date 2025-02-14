package com.web.service.domain.exception;

public class UnprocessableAction extends RuntimeException {
    public UnprocessableAction(String message) {
        super(message);
    }
}
