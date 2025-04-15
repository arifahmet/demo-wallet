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
                     "blockedBalance": "0.00",
                     "activeForWithdraw": true
                 },
                 {
                     "walletName": "eur_wallet",
                     "currency": "USD",
                     "usableBalance": "0.00",
                     "blockedBalance": "0.00",
                     "activeForWithdraw": true
                 },
                 {
                     "walletName": "eurr_wallet",
                     "currency": "EUR",
                     "usableBalance": "0.00",
                     "blockedBalance": "0.00",
                     "activeForWithdraw": true
                 }
            ]""";
    public static final String CREATE_WALLET_REQUEST = """
            {
                 "walletName": "eur_wallet",
                 "currency": "EUR"
            }""";
    public static final String CHANGE_WALLET_WITHDRAW_STATUS_REQUEST = """
            {
                "walletName": "test_wallet",
                "activeForWithdraw": false
            }""";
}
