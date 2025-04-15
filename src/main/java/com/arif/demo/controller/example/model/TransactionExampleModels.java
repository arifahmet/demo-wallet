package com.arif.demo.controller.example.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TransactionExampleModels {
    public static final String CREATE_TRANSACTION_REQUEST = """
            {
                 "walletName": "test_wallet",
                 "transactionType": "WITHDRAW",
                 "amount": 1001
            }""";
    public static final String GET_USER_TRANSACTIONS_RESPONSE = """
            [
                  {
                      "walletName": "test_wallet",
                      "transactionKey": "c8bf8f40-bb7c-4767-b6d9-77aa4084ed4f",
                      "transactionType": "DEPOSIT",
                      "transactionStatus": "APPROVED",
                      "oppositePartyType": "IBAN",
                      "amount": "1000.00",
                      "description": null,
                      "created": "2025-04-15 06:58:07",
                      "statusChangeTime": "2025-04-15 06:58:19"
                  },
                  {
                      "walletName": "test_wallet",
                      "transactionKey": "286e01ed-7a47-4307-b863-993947f92c4e",
                      "transactionType": "DEPOSIT",
                      "transactionStatus": "APPROVED",
                      "oppositePartyType": "IBAN",
                      "amount": "1000.00",
                      "description": null,
                      "created": "2025-04-15 06:58:41",
                      "statusChangeTime": "2025-04-15 06:58:53"
                  },
                  {
                      "walletName": "test_wallet",
                      "transactionKey": "9d76a96a-7ecb-4b12-bdcf-738222d37719",
                      "transactionType": "WITHDRAW",
                      "transactionStatus": "APPROVED",
                      "oppositePartyType": "BANK_ACCOUNT",
                      "amount": "1001.00",
                      "description": null,
                      "created": "2025-04-15 07:00:06",
                      "statusChangeTime": "2025-04-15 07:00:30"
                  }
            ]""";
    public static final String TRANSACTIONS_RESPONSE = """
            {
              "transactionKey": "09681f9d-d708-4791-bc7a-34e711aeb7f1",
              "transactionStatus": "PENDING"
            }""";
    public static final String APPROVE_TRANSACTION_STATUS_REQUEST = """
            {
              "transactionKey": "09681f9d-d708-4791-bc7a-34e711aeb7f1",
              "transactionStatus": "APPROVED"
            }""";

    public static final String DENY_TRANSACTION_STATUS_REQUEST = """
            {
              "transactionKey": "09681f9d-d708-4791-bc7a-34e711aeb7f1",
              "transactionStatus": "DENIED"
            }""";
}
