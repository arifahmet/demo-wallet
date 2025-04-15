package com.arif.demo.controller;

import com.arif.demo.controller.example.model.UserExampleModels;
import com.arif.demo.model.web.user.GetUserResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/user")
@Tag(name = "User", description = "User Related Endpoints")
public interface UserOperation {

    @GetMapping
    @Operation(summary = "Get User",
            responses = {@ApiResponse(responseCode = "200",
                    description = "Returns Signed In User Info",
                    content = {@Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {@ExampleObject(name = "User Info Response",
                                    value = UserExampleModels.GET_USER_RESPONSE,
                                    summary = "User Info Response Model")},
                            schema = @Schema(implementation = GetUserResponseDto.class))})})
    Mono<GetUserResponseDto> getUser();
}
