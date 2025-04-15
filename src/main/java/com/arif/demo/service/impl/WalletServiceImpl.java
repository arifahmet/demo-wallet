package com.arif.demo.service.impl;

import com.arif.demo.exception.model.InsufficientBalanceException;
import com.arif.demo.exception.model.WalletNotFoundException;
import com.arif.demo.exception.model.WalletWithdrawDisableException;
import com.arif.demo.model.entity.TransactionEntity;
import com.arif.demo.model.entity.WalletEntity;
import com.arif.demo.model.enums.TransactionStatusEnum;
import com.arif.demo.model.enums.TransactionTypeEnum;
import com.arif.demo.model.web.wallet.ChanceShoppingStatusRequestDto;
import com.arif.demo.model.web.wallet.ChanceWithdrawStatusRequestDto;
import com.arif.demo.model.web.wallet.CreateWalletRequestDto;
import com.arif.demo.model.web.wallet.GetUserWalletResponseDto;
import com.arif.demo.repository.WalletRepository;
import com.arif.demo.service.UserService;
import com.arif.demo.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {
    private final WalletRepository walletRepository;
    private final UserService userService;

    @Override
    public Mono<Void> createWallet(CreateWalletRequestDto createWalletRequest) {
        return userService.getUser().flatMap(user ->
                walletRepository.save(WalletEntity.of(user, createWalletRequest))).then();
    }

    @Override
    public Flux<GetUserWalletResponseDto> getUserWallets() {
        return userService.getUser()
                .flatMapMany(userEntity -> walletRepository.findUserWallets(userEntity.getUserKey()))
                .map(GetUserWalletResponseDto::of);
    }

    @Override
    public Mono<Void> chanceWithdrawStatus(ChanceWithdrawStatusRequestDto request) {
        return getUserWalletByName(request.getWalletName()).flatMap(wallet ->
                walletRepository.updateWithdrawStatus(wallet.getId(), request.isActiveForWithdraw())).then();
    }

    @Override
    public Mono<Void> chanceShoppingStatus(ChanceShoppingStatusRequestDto request) {
        return getUserWalletByName(request.getWalletName()).flatMap(wallet ->
                walletRepository.updateShoppingStatus(wallet.getId(), request.isActiveForShopping())).then();
    }

    @Override
    public Mono<WalletEntity> getUserWalletByName(String walletName) {
        return userService.getUser()
                .flatMap(userEntity -> walletRepository.findWallet(userEntity.getUserKey(), walletName))
                .switchIfEmpty(Mono.error(new WalletNotFoundException()));
    }

    @Override
    public Mono<WalletEntity> changeUserWalletBalance(TransactionEntity transactionEntity) {
        return walletRepository.findById(transactionEntity.getWalletId()).flatMap(walletEntity -> {
            if (!walletEntity.isActiveForWithdraw() && transactionEntity.getTransactionType().equals(TransactionTypeEnum.WITHDRAW)) {
                return Mono.error(new WalletWithdrawDisableException());
            }
            if (hasSufficientUsableBalance(walletEntity, transactionEntity)) {
                var usableAmountChange = getUsableAmountChange(transactionEntity);
                var blockAmountChange = getBlockAmountChange(transactionEntity);
                return walletRepository.changeBalance(walletEntity.getId(), usableAmountChange, blockAmountChange).thenReturn(walletEntity);
            }
            return Mono.error(new InsufficientBalanceException());
        });
    }

    @Override
    public Mono<WalletEntity> getUserWalletById(Long walletId) {
        return walletRepository.findById(walletId)
                .switchIfEmpty(Mono.error(new WalletNotFoundException()));
    }

    private boolean hasSufficientUsableBalance(WalletEntity walletEntity, TransactionEntity transactionEntity) {
        if (TransactionStatusEnum.PENDING.equals(transactionEntity.getTransactionStatus())
                && TransactionTypeEnum.WITHDRAW.equals(transactionEntity.getTransactionType())) {
            BigDecimal adjustedBalance = walletEntity.getUsableBalance().subtract(transactionEntity.getAmount());
            return adjustedBalance.compareTo(BigDecimal.ZERO) >= 0;
        }
        return true;
    }

    private BigDecimal getBlockAmountChange(TransactionEntity transactionEntity) {
        if (TransactionStatusEnum.PENDING.equals(transactionEntity.getTransactionStatus())) {
            return transactionEntity.getAmount();
        }
        return transactionEntity.getAmount().negate();
    }

    private BigDecimal getUsableAmountChange(TransactionEntity transactionEntity) {
        if (TransactionStatusEnum.APPROVED.equals(transactionEntity.getTransactionStatus())) {
            return TransactionTypeEnum.DEPOSIT.equals(transactionEntity.getTransactionType()) ? transactionEntity.getAmount() : BigDecimal.ZERO;
        }
        return TransactionTypeEnum.DEPOSIT.equals(transactionEntity.getTransactionType()) ? BigDecimal.ZERO : transactionEntity.getAmount().negate();
    }
}
