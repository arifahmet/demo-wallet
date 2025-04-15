package com.arif.demo.controller.Impl;

import com.arif.demo.controller.WalletOperation;
import com.arif.demo.model.web.wallet.ChanceShoppingStatusRequestDto;
import com.arif.demo.model.web.wallet.ChanceWithdrawStatusRequestDto;
import com.arif.demo.model.web.wallet.CreateWalletRequestDto;
import com.arif.demo.model.web.wallet.GetUserWalletResponseDto;
import com.arif.demo.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class WalletController implements WalletOperation {

    private final WalletService walletService;

    @Override
    public Flux<GetUserWalletResponseDto> getUserWallets() {
        return walletService.getUserWallets();
    }

    @Override
    public Mono<Void> createWallet(CreateWalletRequestDto createWalletRequest) {
        return walletService.createWallet(createWalletRequest);
    }

    @Override
    public Mono<Void> createWallet(ChanceWithdrawStatusRequestDto request) {
        return walletService.chanceWithdrawStatus(request);
    }

    @Override
    public Mono<Void> chanceShoppingStatus(ChanceShoppingStatusRequestDto request) {
        return walletService.chanceShoppingStatus(request);
    }
}

