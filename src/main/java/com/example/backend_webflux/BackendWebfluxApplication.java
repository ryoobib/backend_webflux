package com.example.backend_webflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class BackendWebfluxApplication {

  public static void main(String[] args) {
    SpringApplication.run(BackendWebfluxApplication.class, args);
  }

}
