package com.example.backendtransporteapi.utils.kafka;

import com.example.backendtransporteapi.model.TransactionModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerConfig {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerConfig.class);

    @KafkaListener(topics = "transactionTopic", groupId = "transporte-group")
    public void consume(TransactionModel transaction) {
        logger.info("Consumed transaction: {}", transaction);
    }
}
