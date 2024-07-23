package com.example.backendtransporteapi.repository;

import com.example.backendtransporteapi.model.TransactionModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends MongoRepository<TransactionModel, String> {
    List<TransactionModel> findByTimestampBetween(LocalDateTime start, LocalDateTime end);
}
