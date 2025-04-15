package com.arif.demo.service.impl;

import com.arif.demo.model.entity.UserEntity;
import com.arif.demo.model.entity.WalletEntity;
import com.arif.demo.model.web.wallet.CreateWalletRequestDto;
import com.arif.demo.model.web.wallet.CurrenyEnum;
import com.arif.demo.repository.WalletRepository;
import com.arif.demo.security.TokenUtil;
import com.arif.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
public class WalletServiceImplTest {

    @InjectMocks
    private WalletServiceImpl walletService;

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private UserService userService;

    @Mock
    private TokenUtil tokenUtil;

    @Test
    public void createWallet_successfulCreation() {
        CreateWalletRequestDto requestDto = new CreateWalletRequestDto();
        requestDto.setWalletName("TestWallet");
        requestDto.setCurrency(CurrenyEnum.USD);

        WalletEntity walletEntity = new WalletEntity();
        walletEntity.setId(1L);
        walletEntity.setWalletName(requestDto.getWalletName());
        walletEntity.setCurrency(requestDto.getCurrency());

        Mockito.when(tokenUtil.getUserKey()).thenReturn(Mono.just("testUserKey"));
        Mockito.when(userService.getUser()).thenReturn(Mono.just(new UserEntity()));
        Mockito.when(walletRepository.save(Mockito.any(WalletEntity.class))).thenReturn(Mono.just(walletEntity));

        StepVerifier.create(walletService.createWallet(requestDto))
                .verifyComplete();

        Mockito.verify(walletRepository, Mockito.times(1)).save(Mockito.any(WalletEntity.class));
    }
}