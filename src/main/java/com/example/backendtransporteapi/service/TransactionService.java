package com.example.backendtransporteapi.service;

import com.example.backendtransporteapi.model.TransactionModel;
import com.example.backendtransporteapi.repository.TransactionRepository;
import com.example.backendtransporteapi.utils.exceptions.*;
import io.github.resilience4j.retry.annotation.Retry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;

@Service
public class TransactionService {

    private static final Logger logger = LogManager.getLogger(TransactionService.class);

    private final TransactionRepository transactionRepository;
    private final KafkaSender<String, TransactionModel> kafkaSender;

    public TransactionService(TransactionRepository transactionRepository, KafkaSender<String, TransactionModel> kafkaSender) {
        this.transactionRepository = transactionRepository;
        this.kafkaSender = kafkaSender;
    }

    @Retry(name = "transactionService")
    public Mono<TransactionModel> saveTransaction(TransactionModel transaction) {
        return transactionRepository.save(transaction)
                .onErrorResume(error -> {
                    logger.error("Database error occurred while saving transaction", error);
                    return Mono.error(new DatabaseException("Error saving the transaction to the database", error));
                })
                .flatMap(savedTransaction ->
                        kafkaSender.send(Mono.just(SenderRecord.create("transactionTopic", null, null, null, savedTransaction, null)))
                                .then(Mono.just(savedTransaction))
                                .onErrorResume(error -> {
                                    logger.error("Kafka error occurred while sending transaction", error);
                                    return Mono.error(new KafkaException("Error sending the transaction to Kafka", error));
                                })
                )
                .onErrorResume(error -> {
                    logger.error("Transaction error occurred", error);
                    return Mono.error(new TransactionException("Error occurred during the transaction process", error));
                });
    }
}
