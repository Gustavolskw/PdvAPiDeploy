package com.web.service.domain.exception;

public class OrderProcessError extends RuntimeException {
    public OrderProcessError(String message) {
        super(message);
    }
}
