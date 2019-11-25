package com.transferwise.banks.demo.config;

import okhttp3.mockwebserver.MockWebServer;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@TestConfiguration
public class MockWebServerConfig {

    @Bean
    public MockWebServer mockWebServer() {
        return new MockWebServer();
    }

    @Bean
    public WebClient webClient() {
        return WebClient.create(mockWebServer().url("/").toString());
    }

}
