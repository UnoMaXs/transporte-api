package com.example.backendtransporteapi.utils.config;

public class Constants {

    private Constants() {
    }

    public static final String TRANSACTION_TOPIC = "transactionTopic";
    public static final String KAFKA_CONSUMER_GROUPID = "transporte-group";
    public static final String KAFKA_BOOTSTRAP_SERVERS = "kafka:9092";
    public static final String PATH_URI = "/api/v1/transactions";
    public static final String PATH_POST = "/save";

}
