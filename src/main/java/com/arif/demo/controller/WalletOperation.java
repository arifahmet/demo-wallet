package com.arif.demo.controller;

import com.arif.demo.controller.example.model.WalletExampleModels;
import com.arif.demo.model.web.user.GetUserResponseDto;
import com.arif.demo.model.web.wallet.ChanceShoppingStatusRequestDto;
import com.arif.demo.model.web.wallet.ChanceWithdrawStatusRequestDto;
import com.arif.demo.model.web.wallet.CreateWalletRequestDto;
import com.arif.demo.model.web.wallet.GetUserWalletResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/wallet")
@Tag(name = "Wallet", description = "Wallet Related Endpoints")
public interface WalletOperation {

    @GetMapping
    @Operation(summary = "Get User Wallets",
            responses = {@ApiResponse(responseCode = "200",
                    description = "Returns User Wallet Info",
                    content = {@Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {@ExampleObject(name = "User Info Response",
                                    value = WalletExampleModels.GET_USER_WALLETS_RESPONSE,
                                    summary = "User Wallet Info Response Model")},
                            schema = @Schema(implementation = GetUserResponseDto.class))})})
    Flux<GetUserWalletResponseDto> getUserWallets();

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create wallet",
            description = "Create a new wallet.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Create Wallet Request Body",
                    required = true,
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {@ExampleObject(name = "Create Wallet Request",
                                    value = WalletExampleModels.CREATE_WALLET_REQUEST,
                                    summary = " Create Wallet Request Model")},
                            schema = @Schema(implementation = CreateWalletRequestDto.class))}))
    Mono<Void> createWallet(@RequestBody @Valid CreateWalletRequestDto createWalletRequest);

    @PostMapping("/withdraw-status")
    @Operation(summary = "Change withdraw status",
            description = "Change wallet withdraw status.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Change Withdraw Status Request Body",
                    required = true,
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {@ExampleObject(name = "Change Withdraw Status Request",
                                    value = WalletExampleModels.CHANGE_WALLET_WITHDRAW_STATUS_REQUEST,
                                    summary = "Change Withdraw Status Request Model")},
                            schema = @Schema(implementation = CreateWalletRequestDto.class))}))
    Mono<Void> createWallet(@RequestBody @Valid ChanceWithdrawStatusRequestDto request);

    @PostMapping("/shopping-status")
    @Operation(summary = "Change shopping active status",
            description = "Change wallet shopping status.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Change Shopping Status Request Body",
                    required = true,
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {@ExampleObject(name = "Change Shopping Status Request",
                                    value = WalletExampleModels.CHANGE_WALLET_SHOPPING_STATUS_REQUEST,
                                    summary = "Change Shopping Status Request Model")},
                            schema = @Schema(implementation = ChanceShoppingStatusRequestDto.class))}))
    Mono<Void> chanceShoppingStatus(@RequestBody @Valid ChanceShoppingStatusRequestDto request);
}
