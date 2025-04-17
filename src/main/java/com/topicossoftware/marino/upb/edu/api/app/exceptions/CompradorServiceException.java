package com.topicossoftware.marino.upb.edu.api.app.exceptions;

import lombok.Data;

@Data
public class CompradorServiceException extends RuntimeException{
    private String errorCode;
    public CompradorServiceException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}