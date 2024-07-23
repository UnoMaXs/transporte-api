package com.example.backendtransporteapi.repository;

import com.example.backendtransporteapi.model.TransactionModel;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.time.LocalDateTime;
import reactor.core.publisher.Flux;

public interface TransactionRepository extends ReactiveCrudRepository<TransactionModel, Long> {
    Flux<TransactionModel> findByTimestampBetween(LocalDateTime start, LocalDateTime end);
}