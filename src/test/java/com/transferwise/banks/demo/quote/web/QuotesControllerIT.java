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
public class QuotesControllerIT extends ServerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void shouldReturnBadRequestWhenNoCustomerId() {
        var sourceAmount = 2200L;
        var quoteJson = "{\n" +
                "  \"sourceAmount\": " + sourceAmount + ",\n" +
                "  \"sourceCurrency\": \"GBP\",\n" +
                "  \"targetCurrency\": \"EUR\"\n" +
                "}";


        webTestClient.post()
                .uri("/quotes")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString())
                .body(fromObject(quoteJson))
                .exchange()
                .expectStatus().isBadRequest();

    }

    @Test
    public void shouldReturnBadRequestWhenNoAmounts() {
        var customerId = 777L;
        var quoteJson = "{\n" +
                "  \"sourceCurrency\": \"GBP\",\n" +
                "  \"targetCurrency\": \"EUR\"\n" +
                "}";


        webTestClient.post()
                .uri("/quotes?customerId=" + customerId)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString())
                .body(fromObject(quoteJson))
                .exchange()
                .expectStatus().isBadRequest();

    }

    @Test
    public void shouldReturnBadRequestWhenNoSourceCurrency() {
        var customerId = 777L;
        var quoteJson = "{\n" +
                "  \"sourceAmount\": 100,\n" +
                "  \"targetCurrency\": \"EUR\"\n" +
                "}";


        webTestClient.post()
                .uri("/quotes?customerId=" + customerId)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString())
                .body(fromObject(quoteJson))
                .exchange()
                .expectStatus().isBadRequest();

    }

    @Test
    public void shouldReturnBadRequestWhenNoTargetCurrency() {
        var customerId = 777L;
        var quoteJson = "{\n" +
                "  \"sourceAmount\": 100,\n" +
                "  \"sourceCurrency\": \"GBP\",\n" +
                "}";


        webTestClient.post()
                .uri("/quotes?customerId=" + customerId)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString())
                .body(fromObject(quoteJson))
                .exchange()
                .expectStatus().isBadRequest();

    }

    @Test
    public void shouldReturnQuote() throws IOException {
        var customerId = 777L;
        var sourceCurrency = "EUR";
        var targetCurrency = "BRL";
        var sourceAmount = 2200L;
        var quoteId = "459b1da2-9acd-4d14-a3e7-c6e8483d1c2a";

        var quoteJson = "{\n" +
                "  \"sourceAmount\": " + sourceAmount + ",\n" +
                "  \"sourceCurrency\": \"" + sourceCurrency + "\",\n" +
                "  \"targetCurrency\": \"" + targetCurrency + "\"\n" +
                "}";

        mockWebServer.enqueue(response("user-credentials.json"));
        mockWebServer.enqueue(response("quote.json"));


        webTestClient.post()
                .uri("/quotes?customerId=" + customerId)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString())
                .body(fromObject(quoteJson))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(quoteId)
                .jsonPath("$.sourceCurrency").isEqualTo(sourceCurrency)
                .jsonPath("$.targetCurrency").isEqualTo(targetCurrency)
                .jsonPath("$.sourceAmount").isEqualTo(sourceAmount);

    }


}