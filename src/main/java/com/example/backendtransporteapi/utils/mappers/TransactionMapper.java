package com.example.backendtransporteapi.utils.mappers;

import com.example.backendtransporteapi.model.TransactionModel;
import com.example.backendtransporteapi.model.dto.TransactionDTO;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    public TransactionModel toModel(TransactionDTO transactionDTO) {
        TransactionModel transaction = new TransactionModel();
        transaction.setTransactionId(transactionDTO.getTransactionId());
        transaction.setTimestamp(transactionDTO.getTimestamp());
        transaction.setDeviceNumber(transactionDTO.getDeviceNumber());
        transaction.setUserId(transactionDTO.getUserId());
        transaction.setGeoPosition(transactionDTO.getGeoPosition());
        transaction.setAmount(transactionDTO.getAmount());
        return transaction;
    }

    public TransactionDTO toDTO(TransactionModel transactionModel) {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setTransactionId(transactionModel.getTransactionId());
        transactionDTO.setTimestamp(transactionModel.getTimestamp());
        transactionDTO.setDeviceNumber(transactionModel.getDeviceNumber());
        transactionDTO.setUserId(transactionModel.getUserId());
        transactionDTO.setGeoPosition(transactionModel.getGeoPosition());
        transactionDTO.setAmount(transactionModel.getAmount());
        return transactionDTO;
    }
}
