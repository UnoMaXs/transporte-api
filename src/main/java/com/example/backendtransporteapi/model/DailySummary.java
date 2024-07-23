package com.example.backendtransporteapi.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "dailySummaries")
public class DailySummary {

    @Id
    private String id;
    private LocalDate date;
    private double totalAmount;
}
