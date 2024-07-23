package com.example.backendtransporteapi.utils.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class MessagesService {

    private final MessageSource messageSource;

    public String getMessage(String code) {
        return messageSource.getMessage(code,null, Locale.getDefault());
    }
}
