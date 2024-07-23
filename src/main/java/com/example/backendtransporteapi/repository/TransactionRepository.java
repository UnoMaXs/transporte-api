package com.example.backendtransporteapi.repository;

import com.example.backendtransporteapi.model.TransactionModel;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

public interface TransactionRepository extends ReactiveCrudRepository<TransactionModel, String> {
    Flux<TransactionModel> findByTimestampBetween(LocalDateTime start, LocalDateTime end);
}