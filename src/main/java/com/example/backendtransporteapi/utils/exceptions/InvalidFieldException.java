package com.example.backendtransporteapi.utils.exceptions;

import lombok.Getter;

import java.util.Map;

@Getter
public class InvalidFieldException extends RuntimeException{

    private final Map<String, String> invalidFields;

    public InvalidFieldException(Map<String, String> invalidFields) {
        this.invalidFields = invalidFields;
    }

}
