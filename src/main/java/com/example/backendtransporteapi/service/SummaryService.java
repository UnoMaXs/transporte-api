package com.example.backendtransporteapi.service;


import com.example.backendtransporteapi.model.DailySummary;
import com.example.backendtransporteapi.model.TransactionModel;
import com.example.backendtransporteapi.repository.DailySummaryRepository;
import com.example.backendtransporteapi.repository.TransactionRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class SummaryService {


    private final TransactionRepository transactionRepository;

    private final DailySummaryRepository dailySummaryRepository;

    private final MongoTemplate mongoTemplate;

    public SummaryService(TransactionRepository transactionRepository, DailySummaryRepository dailySummaryRepository, MongoTemplate mongoTemplate) {
        this.transactionRepository = transactionRepository;
        this.dailySummaryRepository = dailySummaryRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public void generateDailySummary(LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("timestamp").gte(startOfDay).lt(endOfDay)),
                Aggregation.group().sum("amount").as("totalAmount")
        );

        AggregationResults<DailySummary> results = mongoTemplate.aggregate(aggregation, TransactionModel.class, DailySummary.class);
        DailySummary summary = results.getUniqueMappedResult();

        if (summary != null) {
            summary.setDate(date);
            dailySummaryRepository.save(summary);
        }
    }

    public List<DailySummary> getAllSummaries() {
        return dailySummaryRepository.findAll();
    }
}
