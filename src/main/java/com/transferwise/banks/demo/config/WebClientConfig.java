package com.transferwise.banks.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${transferwise.api.url}")
    private String transferWiseApiUrl;

    @Bean
    public WebClient webClient() {
        final ExchangeFilterFunction printlnFilter = (request, next) -> {
            System.out.println("\n\n" + request.method().toString().toUpperCase() + ":\n\nURL:"
                    + request.url().toString() + ":\n\nHeaders:" + request.headers().toString() + "\n\nAttributes:"
                    + request.attributes() + "\n\n");

            return next.exchange(request);
        };
        return WebClient.builder().baseUrl(transferWiseApiUrl).filter(printlnFilter).build();
    }
}
