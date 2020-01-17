package com.transferwise.banks.demo.recipient.web;

import com.google.common.net.HttpHeaders;
import com.transferwise.banks.demo.ServerTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.io.IOException;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;

@AutoConfigureWebTestClient
@DirtiesContext
public class RecipientRequirementsControllerIT extends ServerTest {

    private static final long CUSTOMER_ID = 777L;
    private static final String QUOTE_ID = "459b1da2-9acd-4d14-a3e7-c6e8483d1c2a";

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void shouldRemoveEmailWhenGetRecipientRequirements() throws IOException {
        mockWebServer.enqueue(response("user-credentials.json"));
        mockWebServer.enqueue(response("recipient-requirements.json"));

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/recipient/requirements")
                        .queryParam("customerId", CUSTOMER_ID)
                        .queryParam("quoteId", QUOTE_ID)
                        .build())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[*].type").value(hasItem("iban"))
                .jsonPath("$[*].type").value(not(hasItem("email")));
    }


}