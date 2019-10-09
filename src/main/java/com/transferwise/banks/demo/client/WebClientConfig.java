package com.transferwise.banks.demo.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

import static com.transferwise.banks.demo.client.TransferWisePaths.BASE_URL;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient() {
        final ExchangeFilterFunction printlnFilter = (request, next) -> {
            System.out.println("\n\n" + request.method().toString().toUpperCase() + ":\n\nURL:"
                    + request.url().toString() + ":\n\nHeaders:" + request.headers().toString() + "\n\nAttributes:"
                    + request.attributes() + "\n\n");

            return next.exchange(request);
        };
        return WebClient.builder().baseUrl(BASE_URL).filter(printlnFilter).build();
    }
}
