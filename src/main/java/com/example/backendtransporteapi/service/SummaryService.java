package com.example.backendtransporteapi.service;

import com.example.backendtransporteapi.model.DailySummary;
import com.example.backendtransporteapi.model.TransactionModel;
import com.example.backendtransporteapi.repository.DailySummaryRepository;
import com.example.backendtransporteapi.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class SummaryService {

    private static final Logger logger = LoggerFactory.getLogger(SummaryService.class);
    private final TransactionRepository transactionRepository;
    private final DailySummaryRepository dailySummaryRepository;

    public SummaryService(TransactionRepository transactionRepository, DailySummaryRepository dailySummaryRepository) {
        this.transactionRepository = transactionRepository;
        this.dailySummaryRepository = dailySummaryRepository;
    }

    public void generateDailySummary(LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);

        Mono<List<TransactionModel>> transactionsMono = transactionRepository.findByTimestampBetween(startOfDay, endOfDay).collectList();

        transactionsMono.publishOn(Schedulers.boundedElastic()).doOnNext(transactions -> {
            logger.info("Number of transactions found: {}", transactions.size());

            double totalAmount = transactions.stream().mapToDouble(TransactionModel::getAmount).sum();

            logger.info("Total amount for {}: {}", date, totalAmount);

            DailySummary dailySummary = new DailySummary();
            dailySummary.setDate(date);
            dailySummary.setTotalAmount(totalAmount);

            dailySummaryRepository.save(dailySummary);
        }).block();
    }
}
