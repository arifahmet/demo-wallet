package com.arif.demo.controller;

import com.arif.demo.controller.example.model.TransactionExampleModels;
import com.arif.demo.model.enums.TransactionStatusEnum;
import com.arif.demo.model.enums.TransactionTypeEnum;
import com.arif.demo.model.web.transaction.ChangeTransactionStatusRequestDto;
import com.arif.demo.model.web.transaction.CreateTransactionRequestDto;
import com.arif.demo.model.web.transaction.TransactionResponseDto;
import com.arif.demo.model.web.transaction.UserTransactionResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/transaction")
@Tag(name = "Transaction", description = "Transaction Related Endpoints")
public interface TransactionOperation {

    @GetMapping
    @Operation(summary = "Get User Transactions",
            responses = {@ApiResponse(responseCode = "200",
                    description = "Returns User Transactions",
                    content = {@Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {@ExampleObject(name = "User Info Response",
                                    value = TransactionExampleModels.GET_USER_TRANSACTIONS_RESPONSE,
                                    summary = "User Transactions Info Response Model")},
                            schema = @Schema(implementation = UserTransactionResponseDto.class))})})
    @Parameters({
            @Parameter(name = "walletName",
                    description = "Wallet Name",
                    example = "test_wallet",
                    schema = @Schema(implementation = String.class)),
            @Parameter(name = "status",
                    description = "Transaction Status",
                    example = "APPROVED",
                    schema = @Schema(implementation = TransactionStatusEnum.class)),
            @Parameter(name = "transactionType",
                    description = "Transaction Type",
                    example = "DEPOSIT",
                    schema = @Schema(implementation = TransactionTypeEnum.class))
    })
    Flux<UserTransactionResponseDto> getUserTransactions(@RequestParam(required = false) @Valid @Size(max = 250) String walletName,
                                                         @RequestParam(required = false) TransactionStatusEnum status,
                                                         @RequestParam(required = false) TransactionTypeEnum transactionType);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create Transaction",
            description = "Create a new transactions.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Create Transaction Request Body",
                    required = true,
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {@ExampleObject(name = "Create Transaction Request",
                                    value = TransactionExampleModels.CREATE_TRANSACTION_REQUEST,
                                    summary = " Create Transaction Request Model")},
                            schema = @Schema(implementation = CreateTransactionRequestDto.class))}),
            responses = {@ApiResponse(responseCode = "200",
                    description = "Returns Created Transaction Info",
                    content = {@Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {@ExampleObject(name = "Created Transaction Info Response",
                                    value = TransactionExampleModels.TRANSACTIONS_RESPONSE,
                                    summary = "Created Transaction Info Response Model")},
                            schema = @Schema(implementation = TransactionResponseDto.class))})})
    Mono<TransactionResponseDto> createTransaction(@RequestBody @Valid CreateTransactionRequestDto transactionRequest);

    @PostMapping("/status")
    @Operation(summary = "Approve/Deny Transaction",
            description = "Approve or Deny transaction.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Create Transaction Request Body",
                    required = true,
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {@ExampleObject(name = "Create Transaction Request",
                                    value = TransactionExampleModels.APPROVE_TRANSACTION_STATUS_REQUEST,
                                    summary = " Approve Transaction Request Model"),
                                    @ExampleObject(name = "Create Transaction Request",
                                            value = TransactionExampleModels.DENY_TRANSACTION_STATUS_REQUEST,
                                            summary = " Deny Transaction Request Model")},
                            schema = @Schema(implementation = ChangeTransactionStatusRequestDto.class))}))
    Mono<Void> changeTransactionStatus(@RequestBody @Valid ChangeTransactionStatusRequestDto changeTransactionStatusRequest);
}
