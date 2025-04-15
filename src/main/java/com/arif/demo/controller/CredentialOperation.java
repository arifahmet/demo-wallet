package com.arif.demo.controller;

import com.arif.demo.controller.example.model.CredentialExampleModels;
import com.arif.demo.model.web.credential.SignInRequestDto;
import com.arif.demo.model.web.credential.SignInResponseDto;
import com.arif.demo.model.web.credential.SignUpRequestDto;
import com.arif.demo.model.web.credential.SignUpResponseDto;
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
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/credential")
@Tag(name = "Credential", description = "Credential Related Endpoints")
public interface CredentialOperation {
    @PostMapping("/sign-in")
    @Operation(summary = "Sign In User",
            description = "Authenticate and sign in the user.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Sign In Request Body",
                    required = true,
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {@ExampleObject(name = "Sign In Request",
                                    value = CredentialExampleModels.SIGN_IN_REQUEST,
                                    summary = " Sign In Request Model")},
                            schema = @Schema(implementation = SignInRequestDto.class))}),
            responses = {@ApiResponse(responseCode = "200",
                    description = "Returns Registered User",
                    content = {@Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {@ExampleObject(name = "Sign In Response",
                                    value = CredentialExampleModels.SIGN_IN_RESPONSE,
                                    summary = "Sign In Response Model")},
                            schema = @Schema(implementation = SignInResponseDto.class))})})
    Mono<SignInResponseDto> signIn(@RequestBody @Valid SignInRequestDto signupRequest);

    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Sign Up User",
            description = "Register a new user.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Sign Up User Request Body",
                    required = true,
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {@ExampleObject(name = "Sign Up User Request",
                                    value = CredentialExampleModels.SIGN_UP_USER_REQUEST,
                                    summary = " Sign Up User Request Model")},
                            schema = @Schema(implementation = SignUpRequestDto.class))}),
            responses = {@ApiResponse(responseCode = "201",
                    description = "Returns Signed User",
                    content = {@Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {@ExampleObject(name = "Sign Up User Response",
                                    value = CredentialExampleModels.SING_UP_USER_RESPONSE,
                                    summary = "Sign Up User Response Model")},
                            schema = @Schema(implementation = SignUpResponseDto.class))})})
    Mono<SignUpResponseDto> signup(@RequestBody @Valid SignUpRequestDto signupRequest);
}
