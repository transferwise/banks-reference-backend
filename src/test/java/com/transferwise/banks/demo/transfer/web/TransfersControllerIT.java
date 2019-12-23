package com.transferwise.banks.demo.transfer.web;


import com.google.common.net.HttpHeaders;
import com.transferwise.banks.demo.ServerTest;
import com.transferwise.banks.demo.support.CustomMockWebServerDispatcher;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;

@AutoConfigureWebTestClient
@DirtiesContext
public class TransfersControllerIT extends ServerTest {

    private static final long CUSTOMER_ID = 777L;
    private static final String QUOTE_ID = "459b1da2-9acd-4d14-a3e7-c6e8483d1c2a";
    private static final long RECIPIENT_ID = 1234L;

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private DataSource dataSource;


    @Test
    public void shouldReturnBadRequestWhenTransferSummaryWithNoCustomerId() {
        webTestClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/transfers/summary")
                        .queryParam("quoteId", QUOTE_ID)
                        .queryParam("recipientId", RECIPIENT_ID)
                        .build())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString())
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    public void shouldReturnBadRequestWhenTransferSummaryWithNoQuoteId() {
        webTestClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/transfers/summary")
                        .queryParam("customerId", CUSTOMER_ID)
                        .queryParam("recipientId", RECIPIENT_ID)
                        .build())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString())
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    public void shouldReturnBadRequestWhenTransferSummaryWithNoRecipientId() {
        webTestClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/transfers/summary")
                        .queryParam("customerId", CUSTOMER_ID)
                        .queryParam("quoteId", QUOTE_ID)
                        .build())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString())
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    public void shouldReturnTransferSummary() {
        mockWebServer.setDispatcher(new CustomMockWebServerDispatcher()
                .withPathToFile("oauth", "user-credentials.json")
                .withPathToFile("quote", "quote.json")
                .withPathToFile("accounts", "get-recipient.json")
                .withPathToFile("transfer-requirements", "transfer-requirements.json"));

        webTestClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/transfers/summary")
                        .queryParam("customerId", CUSTOMER_ID)
                        .queryParam("quoteId", QUOTE_ID)
                        .queryParam("recipientId", RECIPIENT_ID)
                        .build())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.quoteId").isEqualTo(QUOTE_ID)
                .jsonPath("$.recipientId").isEqualTo(RECIPIENT_ID)
                .jsonPath("$.transferReferenceValidation.minLength").isEqualTo(1)
                .jsonPath("$.transferReferenceValidation.maxLength").isEqualTo(20)
                .jsonPath("$.transferReferenceValidation.validationRegexp").isEmpty();
    }

    @Test
    public void shouldReturnBadRequestWhenCreateTransferWithNoCustomerId() {
        webTestClient.post()
                .uri("/transfers")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString())
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    public void shouldReturnBadRequestWhenCreateTransferWithNoBody() {
        webTestClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/transfers")
                        .queryParam("customerId", CUSTOMER_ID)
                        .build())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString())
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    public void shouldCreateSaveAndReturnTransfer() throws SQLException, InterruptedException {
        var transferId = 468956L;
        var reference = "my reference";

        var createTransferJson = "{\n" +
                "  \"recipientId\": 1234,\n" +
                "  \"quoteId\": \"" + QUOTE_ID + "\",\n" +
                "  \"details\": {\n" +
                "    \"reference\": \"" + reference + "\"\n" +
                "  }\n" +
                "}";

        mockWebServer.setDispatcher(new CustomMockWebServerDispatcher()
                .withPathToFile("oauth", "user-credentials.json")
                .withPathToFile("quote", "quote.json")
                .withPathToFile("accounts", "get-recipient.json")
                .withPathToFile("transfer", "transfer.json"));


        webTestClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/transfers")
                        .queryParam("customerId", CUSTOMER_ID)
                        .build())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString())
                .body(fromObject(createTransferJson))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(transferId)
                .jsonPath("$.recipientId").isEqualTo(RECIPIENT_ID)
                .jsonPath("$.quoteId").isEqualTo(QUOTE_ID)
                .jsonPath("$.reference").isEqualTo(reference);

        Thread.sleep(500); //waiting for customer transfer to be saved async
        Connection connection = dataSource.getConnection();

        String customerTransfersQuery = "select * from customer_transfers where customer_id = 777";
        ResultSet customerTransfersResult = connection.createStatement().executeQuery(customerTransfersQuery);

        int numberOfCustomerTransfers = 0;
        while (customerTransfersResult.next()) {
            numberOfCustomerTransfers++;
            assertThat(customerTransfersResult.getLong("customer_id")).isEqualTo(CUSTOMER_ID);
            assertThat(customerTransfersResult.getString("quote_id")).isEqualTo(QUOTE_ID);
            assertThat(customerTransfersResult.getString("reference")).isEqualTo(reference);

        }
        assertThat(numberOfCustomerTransfers).isEqualTo(1);
    }


}