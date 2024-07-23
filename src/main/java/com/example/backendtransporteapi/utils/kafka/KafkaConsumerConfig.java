package com.example.backendtransporteapi.utils.kafka;

import com.example.backendtransporteapi.model.TransactionModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static com.example.backendtransporteapi.utils.config.Constants.KAFKA_CONSUMER_GROUPID;
import static com.example.backendtransporteapi.utils.config.Constants.TRANSACTION_TOPIC;

@Service
public class KafkaConsumerConfig {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerConfig.class);

    @KafkaListener(topics = TRANSACTION_TOPIC, groupId = KAFKA_CONSUMER_GROUPID)
    public void consume(TransactionModel transaction) {
        logger.info("Consumed transaction: {}", transaction);
    }
}
