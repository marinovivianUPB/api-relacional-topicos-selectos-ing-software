package com.topicossoftware.marino.upb.edu.api.app.exceptions;

import lombok.Data;

@Data
public class UsuarioServiceException extends RuntimeException{
    private String errorCode;
    public UsuarioServiceException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}