package com.example.backendtransporteapi.utils.exceptions;

import com.example.backendtransporteapi.model.dto.response.ErrorResponse;
import com.example.backendtransporteapi.utils.config.MessagesService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice(annotations = RestController.class)
public class ControllerAdvice {

    private final MessagesService messagesService;
    private static final Logger LOGGER = LogManager.getLogger(ControllerAdvice.class);

    public ControllerAdvice(MessagesService messagesService) {
        this.messagesService = messagesService;
    }

    @ExceptionHandler(NotValidArgumentException.class)
    public ResponseEntity<ErrorResponse> handleNotValidArgumentException(NotValidArgumentException exception) {
        return getErrorResponseEntity(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException exception) {
        return getErrorResponseEntity(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<ErrorResponse> handleDatabaseException(DatabaseException exception) {
        return getErrorResponseEntity(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(KafkaException.class)
    public ResponseEntity<ErrorResponse> handleKafkaException(KafkaException exception) {
        return getErrorResponseEntity(exception, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(TransactionException.class)
    public ResponseEntity<ErrorResponse> handleTransactionException(TransactionException exception) {
        return getErrorResponseEntity(exception, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ErrorResponse> getErrorResponseEntity(Exception exception, HttpStatus httpStatus) {
        String message = messagesService.getMessage(exception.getMessage());
        LOGGER.error("Exception occurred: ", exception);
        ErrorResponse errorResponse = new ErrorResponse(message, httpStatus);
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }
}
