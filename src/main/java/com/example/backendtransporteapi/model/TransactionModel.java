package com.example.backendtransporteapi.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "transactions")
public class TransactionModel {
    
    @Id
    private String transactionId;
    private LocalDateTime timestamp;
    private String deviceNumber;
    private String userId;
    private String geoPosition;
    private double amount;

}

