package com.transferwise.banks.demo.quote.web;

import com.google.common.net.HttpHeaders;
import com.transferwise.banks.demo.ServerTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.io.IOException;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;

@AutoConfigureWebTestClient
public class AnonymousQuotesControllerIT extends ServerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void shouldReturnBadRequestWhenNoAmounts() {
        var anonymousQuoteJson = "{\n" +
                "  \"sourceCurrency\": \"GBP\",\n" +
                "  \"targetCurrency\": \"EUR\"\n" +
                "}";


        webTestClient.post()
                .uri("/anonymous-quotes")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString())
                .body(fromObject(anonymousQuoteJson))
                .exchange()
                .expectStatus().isBadRequest();

    }

    @Test
    public void shouldReturnBadRequestWhenNoSourceCurrency() {
        var anonymousQuoteJson = "{\n" +
                "  \"sourceAmount\": 100,\n" +
                "  \"targetCurrency\": \"EUR\"\n" +
                "}";


        webTestClient.post()
                .uri("/anonymous-quotes")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString())
                .body(fromObject(anonymousQuoteJson))
                .exchange()
                .expectStatus().isBadRequest();

    }

    @Test
    public void shouldReturnBadRequestWhenNoTargetCurrency() {
        var anonymousQuoteJson = "{\n" +
                "  \"sourceAmount\": 100,\n" +
                "  \"sourceCurrency\": \"GBP\",\n" +
                "}";


        webTestClient.post()
                .uri("/anonymous-quotes")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString())
                .body(fromObject(anonymousQuoteJson))
                .exchange()
                .expectStatus().isBadRequest();

    }

    @Test
    public void shouldReturnAnonymousQuote() throws IOException {
        var sourceCurrency = "EUR";
        var targetCurrency = "BRL";
        var sourceAmount = 2200L;
        var quoteId = "459b1da2-9acd-4d14-a3e7-c6e8483d1c2a";

        var anonymousQuoteJson = "{\n" +
                "  \"sourceAmount\": " + sourceAmount + ",\n" +
                "  \"sourceCurrency\": \"" + sourceCurrency + "\",\n" +
                "  \"targetCurrency\": \"" + targetCurrency + "\"\n" +
                "}";

        mockWebServer.enqueue(response("quote.json"));


        webTestClient.post()
                .uri("/anonymous-quotes")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString())
                .body(fromObject(anonymousQuoteJson))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(quoteId)
                .jsonPath("$.sourceCurrency").isEqualTo(sourceCurrency)
                .jsonPath("$.targetCurrency").isEqualTo(targetCurrency)
                .jsonPath("$.sourceAmount").isEqualTo(sourceAmount);

    }


}