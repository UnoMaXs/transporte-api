package com.example.backendtransporteapi.service;
import com.example.backendtransporteapi.model.TransactionModel;
import com.example.backendtransporteapi.repository.TransactionRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final KafkaTemplate<String, TransactionModel> kafkaTemplate;


    public TransactionService(TransactionRepository transactionRepository, KafkaTemplate<String, TransactionModel> kafkaTemplate) {
        this.transactionRepository = transactionRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public TransactionModel saveTransaction(TransactionModel transaction) {
        TransactionModel savedTransaction = transactionRepository.save(transaction);
        kafkaTemplate.send("transactionTopic", savedTransaction);
        return savedTransaction;
    }
    public List<TransactionModel> getTransactions(){
        return transactionRepository.findAll();
    }

}

