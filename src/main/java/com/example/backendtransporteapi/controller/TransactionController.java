package com.example.backendtransporteapi.controller;

import com.example.backendtransporteapi.model.TransactionModel;
import com.example.backendtransporteapi.model.dto.TransactionDTO;
import com.example.backendtransporteapi.service.TransactionService;
import com.example.backendtransporteapi.utils.mappers.TransactionMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final TransactionService transactionService;
    private final TransactionMapper transactionMapper;

    public TransactionController(TransactionService transactionService, TransactionMapper transactionMapper) {
        this.transactionService = transactionService;
        this.transactionMapper = transactionMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<TransactionDTO> createTransaction(@RequestBody TransactionDTO transactionDTO) {
        TransactionModel transactionModel = transactionMapper.toModel(transactionDTO);
        return transactionService.saveTransaction(transactionModel)
                .map(transactionMapper::toDTO);
    }
}
