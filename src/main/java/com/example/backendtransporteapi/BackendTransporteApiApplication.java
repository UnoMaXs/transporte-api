package com.example.backendtransporteapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableRetry
public class BackendTransporteApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendTransporteApiApplication.class, args);
    }

}
