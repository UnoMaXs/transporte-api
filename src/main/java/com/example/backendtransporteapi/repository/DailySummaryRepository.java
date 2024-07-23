package com.example.backendtransporteapi.repository;

import com.example.backendtransporteapi.model.DailySummary;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface DailySummaryRepository extends MongoRepository<DailySummary, LocalDate> {
}
