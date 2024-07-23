package com.example.backendtransporteapi.service;

import com.example.backendtransporteapi.model.TransactionModel;
import com.example.backendtransporteapi.repository.TransactionRepository;
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
}
