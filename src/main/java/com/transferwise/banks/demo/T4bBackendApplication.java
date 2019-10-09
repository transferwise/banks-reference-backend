package com.transferwise.banks.demo;

import com.transferwise.banks.demo.client.TransferWiseBankConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(TransferWiseBankConfig.class)
public class T4bBackendApplication {

    public static void main(final String[] args) {
        SpringApplication.run(T4bBackendApplication.class, args);
    }
}
