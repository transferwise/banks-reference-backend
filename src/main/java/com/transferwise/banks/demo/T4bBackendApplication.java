package com.transferwise.banks.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
public class T4bBackendApplication {

    public static void main(final String[] args) {
        SpringApplication.run(T4bBackendApplication.class, args);
    }

}
