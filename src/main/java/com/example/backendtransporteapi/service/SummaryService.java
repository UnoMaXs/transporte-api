package com.example.backendtransporteapi.service;

import com.example.backendtransporteapi.model.DailySummary;
import com.example.backendtransporteapi.model.TransactionModel;
import com.example.backendtransporteapi.repository.DailySummaryRepository;
import com.example.backendtransporteapi.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class SummaryService {

    private final TransactionRepository transactionRepository;

    private final DailySummaryRepository dailySummaryRepository;

    public SummaryService(TransactionRepository transactionRepository, DailySummaryRepository dailySummaryRepository) {
        this.transactionRepository = transactionRepository;
        this.dailySummaryRepository = dailySummaryRepository;
    }

    public void generateDailySummary(LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);

        List<TransactionModel> transactions = transactionRepository.findByTimestampBetween(startOfDay, endOfDay);

        System.out.println("Number of transactions found: " + transactions.size());

        double totalAmount = transactions.stream().mapToDouble(TransactionModel::getAmount).sum();

        System.out.println("Total amount for " + date + ": " + totalAmount);

        DailySummary dailySummary = new DailySummary();
        dailySummary.setDate(date);
        dailySummary.setTotalAmount(totalAmount);

        dailySummaryRepository.save(dailySummary);
    }
}
