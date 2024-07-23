package com.example.backendtransporteapi.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Document(collection = "daily_summaries")
public class DailySummary {
    @Id
    private LocalDate date;
    private double totalAmount;
}
