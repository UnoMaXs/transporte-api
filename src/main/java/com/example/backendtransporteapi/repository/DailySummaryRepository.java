package com.example.backendtransporteapi.repository;

import com.example.backendtransporteapi.model.DailySummary;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailySummaryRepository extends MongoRepository<DailySummary, String> {

}
