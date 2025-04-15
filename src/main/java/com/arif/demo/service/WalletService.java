package com.arif.demo.service;

import com.arif.demo.model.entity.TransactionEntity;
import com.arif.demo.model.entity.WalletEntity;
import com.arif.demo.model.web.wallet.ChanceWithdrawStatusRequestDto;
import com.arif.demo.model.web.wallet.CreateWalletRequestDto;
import com.arif.demo.model.web.wallet.GetUserWalletResponseDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface WalletService {

    Mono<Void> createWallet(CreateWalletRequestDto createWalletRequest);

    Flux<GetUserWalletResponseDto> getUserWallets();

    Mono<Void> chanceWithdrawStatus(ChanceWithdrawStatusRequestDto request);

    Mono<WalletEntity> getUserWalletByName(String walletName);

    Mono<WalletEntity> changeUserWalletBalance(TransactionEntity transaction);

    Mono<WalletEntity> getUserWalletById(Long walletId);
}
