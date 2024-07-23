package com.example.backendtransporteapi.utils.exceptions;


public class NotValidArgumentException extends RuntimeException {

    public NotValidArgumentException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotValidArgumentException(String message) {
        super(message);
    }


}
