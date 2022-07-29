package com.example.backend_webflux.annotation.comment;

import com.example.backend_webflux.domain.Comment;
import com.example.backend_webflux.domain.Scrap;
import com.example.backend_webflux.dto.CommentDto;
import com.example.backend_webflux.dto.ScrapDto;
import exception.ExceptionResponse;
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
        method = RequestMethod.POST,
        operation =
        @Operation(
            description = "Comment scrap",
            operationId = "createComment",
            tags = "comments",
            requestBody =
            @RequestBody(
                description = "Comment to create",
                required = true,
                content = @Content(schema = @Schema(implementation = CommentDto.class,
                    requiredProperties = {"content", "postId", "userId"}))),
            responses = {
                @ApiResponse(
                    responseCode = "200",
                    description = "Create Comment response",
                    content = {
                        @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Comment.class))
                    }),
                @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request response",
                    content = {
                        @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ExceptionResponse.class))
                    })
            }))
})
public @interface CreateCommentApiInfo {

}
