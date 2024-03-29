package com.example.backend_webflux.annotation.user;

import com.example.backend_webflux.domain.User;
import com.example.backend_webflux.exception.ExceptionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@RouterOperations({
    @RouterOperation(
        method = RequestMethod.PUT,
        operation =
        @Operation(
            description = "Modify user by id",
            operationId = "modifyUser",
            tags = "users",
            requestBody =
            @RequestBody(
                description = "User to modify",
                required = true,
                content = @Content(schema = @Schema(implementation = User.class,
                    requiredProperties = {"name", "email", "password"}))),
            responses = {
                @ApiResponse(
                    responseCode = "200",
                    description = "Modify user by id response",
                    content = {
                        @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = User.class))
                    }),
                @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request response",
                    content = {
                        @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ExceptionResponse.class))
                    }),
                @ApiResponse(
                    responseCode = "404",
                    description = "Not found response",
                    content = {
                        @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ExceptionResponse.class))
                    })
            }))
})
public @interface ModifyUserByIdApiInfo {

}
