package com.arif.demo.controller.example.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WalletExampleModels {
    public static final String GET_USER_WALLETS_RESPONSE = """
            [
                {
                    "walletName": "test_wallet",
                    "currency": "USD",
                    "usableBalance": "0.00",
                    "blockedBalance": "-1001.00"
                },
                {
                    "walletName": "eur_wallet",
                    "currency": "EUR",
                    "usableBalance": "0.00",
                    "blockedBalance": "0.00"
                }
            ]""";
    public static final String CREATE_WALLET_REQUEST = """
            {
                 "walletName": "eur_wallet",
                 "currency": "EUR"
            }""";
}
