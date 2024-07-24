package com.example.backendtransporteapi.service;

import com.example.backendtransporteapi.model.DailySummary;
import com.example.backendtransporteapi.model.TransactionModel;
import com.example.backendtransporteapi.repository.DailySummaryRepository;
import com.example.backendtransporteapi.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SummaryServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private DailySummaryRepository dailySummaryRepository;

    @InjectMocks
    private SummaryService summaryService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
     void testGenerateDailySummary() {
        LocalDate date = LocalDate.of(2024, 7, 24);
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);

        TransactionModel transaction1 = new TransactionModel();
        transaction1.setAmount(100.0);

        TransactionModel transaction2 = new TransactionModel();
        transaction2.setAmount(200.0);

        List<TransactionModel> transactions = Arrays.asList(transaction1, transaction2);
        when(transactionRepository.findByTimestampBetween(startOfDay, endOfDay)).thenReturn(Flux.fromIterable(transactions));
        when(dailySummaryRepository.save(any(DailySummary.class))).thenReturn(null);

        summaryService.generateDailySummary(date);

        ArgumentCaptor<DailySummary> captor = ArgumentCaptor.forClass(DailySummary.class);
        verify(dailySummaryRepository, times(1)).save(captor.capture());
        DailySummary capturedSummary = captor.getValue();

        assertEquals(date, capturedSummary.getDate());
        assertEquals(300.0, capturedSummary.getTotalAmount(), 0.001);
    }
}
