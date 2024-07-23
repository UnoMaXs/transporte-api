package com.example.backendtransporteapi.controller;

import com.example.backendtransporteapi.model.TransactionModel;
import com.example.backendtransporteapi.model.dto.request.TransactionRequest;
import com.example.backendtransporteapi.model.dto.response.TransactionResponse;
import com.example.backendtransporteapi.service.TransactionService;
import com.example.backendtransporteapi.utils.mappers.TransactionMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import static com.example.backendtransporteapi.utils.config.Constants.PATH_POST;
import static com.example.backendtransporteapi.utils.config.Constants.PATH_URI;

@RestController
@RequestMapping(PATH_URI)
public class TransactionController {

    private final TransactionService transactionService;
    private final TransactionMapper transactionMapper;

    public TransactionController(TransactionService transactionService, TransactionMapper transactionMapper) {
        this.transactionService = transactionService;
        this.transactionMapper = transactionMapper;
    }

    @PostMapping(PATH_POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<TransactionResponse> createTransaction(@RequestBody TransactionRequest transactionRequest) {
        TransactionModel transactionModel = transactionMapper.toModel(transactionRequest);
        return transactionService.saveTransaction(transactionModel)
                .map(transactionMapper::toResponse);
    }
}
