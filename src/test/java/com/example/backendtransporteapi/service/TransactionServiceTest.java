package com.example.backendtransporteapi.service;

import com.example.backendtransporteapi.model.TransactionModel;
import com.example.backendtransporteapi.repository.TransactionRepository;
import com.example.backendtransporteapi.utils.exceptions.DatabaseException;
import com.example.backendtransporteapi.utils.exceptions.KafkaException;
import com.example.backendtransporteapi.utils.exceptions.TransactionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private KafkaSender<String, TransactionModel> kafkaSender;

    @InjectMocks
    private TransactionService transactionService;

    private TransactionModel transactionModel;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        transactionModel = new TransactionModel("1", LocalDateTime.now(), "device1", "user1", "geo1", 100.0);
    }

    @Test
    void saveTransaction_shouldSaveAndSendToKafka() {
        // Arrange
        when(transactionRepository.save(any(TransactionModel.class))).thenReturn(Mono.just(transactionModel));
        when(kafkaSender.send(any(Mono.class))).thenReturn(Flux.empty());

        // Act
        Mono<TransactionModel> result = transactionService.saveTransaction(transactionModel);

        // Assert
        StepVerifier.create(result)
                .expectNext(transactionModel)
                .verifyComplete();

        verify(transactionRepository).save(transactionModel);
        verify(kafkaSender).send(any(Mono.class));
    }

    @Test
    void saveTransaction_shouldHandleErrorWhenSaving() {
        // Arrange
        when(transactionRepository.save(any(TransactionModel.class))).thenReturn(Mono.error(new RuntimeException("Database error")));

        // Act
        Mono<TransactionModel> result = transactionService.saveTransaction(transactionModel);

        // Assert
        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof TransactionException &&
                        throwable.getMessage().equals("Error occurred during the transaction process") &&
                        throwable.getCause() != null &&
                        throwable.getCause() instanceof DatabaseException &&
                        throwable.getCause().getMessage().equals("Error saving the transaction to the database"))
                .verify();

        verify(transactionRepository).save(transactionModel);
        verify(kafkaSender, never()).send(any(Mono.class));
    }

    @Test
    void saveTransaction_shouldHandleErrorWhenSendingToKafka() {
        // Arrange
        when(transactionRepository.save(any(TransactionModel.class))).thenReturn(Mono.just(transactionModel));
        when(kafkaSender.send(any(Mono.class))).thenReturn(Flux.error(new RuntimeException("Kafka error")));

        // Act
        Mono<TransactionModel> result = transactionService.saveTransaction(transactionModel);

        // Assert
        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof TransactionException &&
                        throwable.getMessage().equals("Error occurred during the transaction process") &&
                        throwable.getCause() != null &&
                        throwable.getCause() instanceof KafkaException &&
                        throwable.getCause().getMessage().equals("Error sending the transaction to Kafka"))
                .verify();

        verify(transactionRepository).save(transactionModel);
        verify(kafkaSender).send(any(Mono.class));
    }
}
