package com.example.backend_webflux.annotation.comment;

import com.example.backend_webflux.domain.Comment;
import com.example.backend_webflux.domain.Scrap;
import exception.ExceptionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
        method = RequestMethod.GET,
        operation =
        @Operation(
            description = "Get all comments ",
            operationId = "getAllComment",
            tags = "comments",
            responses = {
                @ApiResponse(
                    responseCode = "200",
                    description = "Get all comments endpoint",
                    content = {
                        @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = Comment.class)))
                    }),
                @ApiResponse(
                    responseCode = "400",
                    description = "Not found response",
                    content = {
                        @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ExceptionResponse.class))
                    })
            }))
})
public @interface GetAllCommentApiInfo {

}
