package com.example.backendtransporteapi.service;

import com.example.backendtransporteapi.model.TransactionModel;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TransactionConsumer {

    @KafkaListener(topics = "transactionTopic", groupId = "transporte-group")
    public void consume(TransactionModel transaction) {
        System.out.println("Consumed transaction: " + transaction);
    }
}
