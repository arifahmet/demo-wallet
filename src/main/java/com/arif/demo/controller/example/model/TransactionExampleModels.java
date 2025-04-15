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
                     "transactionKey": "021afc16-037a-4c38-8e20-111ca320a331",
                     "transactionType": "DEPOSIT",
                     "transactionStatus": "APPROVED",
                     "amount": "100.00",
                     "description": null,
                     "statusChangedAt": null
                 },
                 {
                     "walletName": "test_wallet",
                     "transactionKey": "4edc261a-a640-4d2f-bd62-0543200fbb07",
                     "transactionType": "WITHDRAW",
                     "transactionStatus": "APPROVED",
                     "amount": "50.00",
                     "description": null,
                     "statusChangedAt": null
                 },
                 {
                     "walletName": "test_wallet",
                     "transactionKey": "3c4c8b4c-e89b-4876-8b54-41057a1eb484",
                     "transactionType": "DEPOSIT",
                     "transactionStatus": "APPROVED",
                     "amount": "999.00",
                     "description": null,
                     "statusChangedAt": null
                 },
                 {
                     "walletName": "test_wallet",
                     "transactionKey": "3c2f871b-1329-46df-b231-84924f710e76",
                     "transactionType": "WITHDRAW",
                     "transactionStatus": "PENDING",
                     "amount": "1001.00",
                     "description": null,
                     "statusChangedAt": null
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
