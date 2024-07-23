package com.example.backendtransporteapi.service;

import com.example.backendtransporteapi.model.TransactionModel;
import com.example.backendtransporteapi.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
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

    public Mono<TransactionModel> saveTransaction(TransactionModel transaction) {
        return transactionRepository.save(transaction)
                .publishOn(Schedulers.boundedElastic())
                .doOnSuccess(savedTransaction -> kafkaSender.send(Mono.just(SenderRecord.create("transactionTopic", null, null, null, savedTransaction, null)))
                        .subscribe());
    }

}
