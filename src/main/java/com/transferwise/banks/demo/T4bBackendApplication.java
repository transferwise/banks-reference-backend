package com.transferwise.banks.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.transferwise.banks.demo.client.TransferWiseBankConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(TransferWiseBankConfig.class)
public class T4bBackendApplication {

    public static void main(final String[] args) {
        SpringApplication.run(T4bBackendApplication.class, args);
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
