package com.example.backendtransporteapi.service;

import com.example.backendtransporteapi.model.TransactionModel;
import com.example.backendtransporteapi.repository.TransactionRepository;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final KafkaSender<String, TransactionModel> kafkaSender;

    public TransactionService(TransactionRepository transactionRepository, KafkaSender<String, TransactionModel> kafkaSender) {
        this.transactionRepository = transactionRepository;
        this.kafkaSender = kafkaSender;
    }

    @Retry(name = "transactionService")
    public Mono<TransactionModel> saveTransaction(TransactionModel transaction) {
        return transactionRepository.save(transaction)
                .flatMap(savedTransaction ->
                        kafkaSender.send(Mono.just(SenderRecord.create("transactionTopic", null, null, null, savedTransaction, null)))
                                .then(Mono.just(savedTransaction))
                )
                .onErrorResume(error -> {
                    return Mono.error(new RuntimeException("Error saving the transaction or sending to Kafka", error));
                });
    }

}
