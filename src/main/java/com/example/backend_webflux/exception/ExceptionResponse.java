package com.example.backend_webflux.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ExceptionResponse {
  @JsonProperty("timestamp")  private Instant timestamp;
  @JsonProperty("path")       private String path;
  @JsonProperty("status")     private Integer status;
  @JsonProperty("error")      private String error;
  @JsonProperty("message")    private String message;
  @JsonProperty("requestId")  private String requestId;
  @JsonProperty("exception")  private String exception;
}
