package com.example.backendtransporteapi.controller;

import com.example.backendtransporteapi.model.TransactionModel;
import com.example.backendtransporteapi.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final TransactionService transactionService;


    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<TransactionModel> createTransaction(@RequestBody TransactionModel transaction) {
        TransactionModel savedTransaction = transactionService.saveTransaction(transaction);
        System.out.println("savedTransaction = " + savedTransaction);
        return new ResponseEntity<>(savedTransaction, HttpStatus.CREATED);
    }

    //TEST
    @GetMapping
    public List<TransactionModel> getTransaction(){
        return transactionService.getTransactions();
    }

}