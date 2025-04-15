package com.arif.demo.controller.example.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TransactionExampleModels {
    public static final String CREATE_TRANSACTION_REQUEST = """
            {
              "amount": 1000,
            }""";
    public static final String GET_USER_TRANSACTIONS_RESPONSE = """
            {
              "walletName": "test_wallet"
            }""";
    public static final String GET_CREATE_TRANSACTIONS_RESPONSE = """
            {
              "transactionKey": "1234567890",
              "transactionStatus": "PENDING"
            }""";
    public static final String APPROVE_TRANSACTION_STATUS_REQUEST = """
            {
              "transactionKey": "1234567890",
              "transactionStatus": "APPROVED"
            }""";

    public static final String DENY_TRANSACTION_STATUS_REQUEST = """
            {
              "transactionKey": "1234567890",
              "transactionStatus": "DENIED"
            }""";
}
