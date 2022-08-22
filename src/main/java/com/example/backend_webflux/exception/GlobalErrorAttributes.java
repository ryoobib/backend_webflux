package com.example.backend_webflux.exception;

import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

@Component
public class GlobalErrorAttributes extends DefaultErrorAttributes {


  @Override
  public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
    Map<String, Object> errorMap = new HashMap<>();
    Throwable error = getError(request);
    if (error instanceof ApiException) {
      ApiException myException = (ApiException) error;
      HttpStatus httpStatus = myException.getError().getHttpStatus();
      int statusCode = myException.getError().getStatus();

      errorMap.put("message", myException.getError().getMessage());
      errorMap.put("error", httpStatus);
      errorMap.put("status", statusCode);

      HttpStatus correspondentStatus = httpStatus;
      errorMap.put("error", correspondentStatus.getReasonPhrase());
    }
    return errorMap;
  }
}
