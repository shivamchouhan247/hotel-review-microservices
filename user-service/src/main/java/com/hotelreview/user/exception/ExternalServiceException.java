package com.hotelreview.user.exception;

public class ExternalServiceException extends RuntimeException {
    public ExternalServiceException(String msg) {
        super(msg);
    }

    public ExternalServiceException(String msg, Throwable ex) {
        super(msg, ex);
    }
}

