package com.example.backendtransporteapi.utils.mappers;

import com.example.backendtransporteapi.model.TransactionModel;
import com.example.backendtransporteapi.model.dto.request.TransactionRequest;
import com.example.backendtransporteapi.model.dto.response.TransactionResponse;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    public TransactionModel toModel(TransactionRequest transactionRequest) {
        TransactionModel transaction = new TransactionModel();
        transaction.setTransactionId(transactionRequest.getTransactionId());
        transaction.setTimestamp(transactionRequest.getTimestamp());
        transaction.setDeviceNumber(transactionRequest.getDeviceNumber());
        transaction.setUserId(transactionRequest.getUserId());
        transaction.setGeoPosition(transactionRequest.getGeoPosition());
        transaction.setAmount(transactionRequest.getAmount());
        return transaction;
    }

    public TransactionResponse toResponse(TransactionModel transactionModel) {
        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setTransactionId(transactionModel.getTransactionId());
        transactionResponse.setTimestamp(transactionModel.getTimestamp());
        transactionResponse.setDeviceNumber(transactionModel.getDeviceNumber());
        transactionResponse.setUserId(transactionModel.getUserId());
        transactionResponse.setGeoPosition(transactionModel.getGeoPosition());
        transactionResponse.setAmount(transactionModel.getAmount());
        return transactionResponse;
    }
    public TransactionRequest toRequest(TransactionModel transactionModel) {
        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setTransactionId(transactionModel.getTransactionId());
        transactionRequest.setTimestamp(transactionModel.getTimestamp());
        transactionRequest.setDeviceNumber(transactionModel.getDeviceNumber());
        transactionRequest.setUserId(transactionModel.getUserId());
        transactionRequest.setGeoPosition(transactionModel.getGeoPosition());
        transactionRequest.setAmount(transactionModel.getAmount());
        return transactionRequest;
    }
}
