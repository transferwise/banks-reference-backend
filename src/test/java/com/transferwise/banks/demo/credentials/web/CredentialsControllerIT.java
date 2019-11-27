package com.transferwise.banks.demo.credentials.web;

import com.google.common.net.HttpHeaders;
import com.transferwise.banks.demo.ServerTest;
import okhttp3.mockwebserver.MockResponse;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureWebTestClient
@DirtiesContext
public class CredentialsControllerIT extends ServerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private DataSource dataSource;

    @Test
    public void shouldSignUpBankCustomer() throws Exception {
        //given
        final Long customerId = 999L; //seeded data for customer without tw account
        mockWebServer.enqueue(response("client-credentials.json"));
        mockWebServer.enqueue(response("user.json"));
        mockWebServer.enqueue(response("user-credentials.json"));
        mockWebServer.enqueue(response("profile.json"));
        String accessToken = "cda71663-3481-4a67-aea0-8c57d56cef24";
        String refreshToken = "5e065dbd-72fe-414b-ad56-9eaa2b45bf31";

        //when & then
        webTestClient.post()
                .uri(uriBuilder -> uriBuilder.path("/user-credentials/sign-up")
                        .queryParam("customerId", customerId)
                        .build())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString())
                .exchange()
                .expectStatus().isOk();

        Thread.sleep(500); //waiting for profile to be saved async
        Connection connection = dataSource.getConnection();

        String twUserQuery = "select * from tw_user where customer_id = 999";
        ResultSet twUserResult = connection.createStatement().executeQuery(twUserQuery);
        int numberOfTwUsers = 0;
        while (twUserResult.next()) {
            numberOfTwUsers++;
            assertThat(twUserResult.getInt("tw_user_id")).isEqualTo(778899);
        }
        assertThat(numberOfTwUsers).isEqualTo(1);

        String twProfileQuery = "select * from tw_profile where customer_id = 999";
        ResultSet twProfileResult = connection.createStatement().executeQuery(twProfileQuery);
        int numberOfTwProfiles = 0;
        while (twProfileResult.next()) {
            numberOfTwProfiles++;
            assertThat(twProfileResult.getInt("tw_profile_id")).isEqualTo(246);
        }
        assertThat(numberOfTwProfiles).isEqualTo(1);

        String twUserTokensQuery = "select * from tw_user_tokens where customer_id = 999";
        ResultSet twUserTokensResult = connection.createStatement().executeQuery(twUserTokensQuery);
        int numberOfTwUserTokens = 0;
        while (twUserTokensResult.next()) {
            numberOfTwUserTokens++;
            assertThat(twUserTokensResult.getString("access_token")).isEqualTo(accessToken);
            assertThat(twUserTokensResult.getString("refresh_token")).isEqualTo(refreshToken);
        }
        assertThat(numberOfTwUserTokens).isEqualTo(1);
    }

    @Test
    public void shouldThrowConflictWhenSignUpThrowsConflict() throws IOException {
        //given
        final Long customerId = 999L; //seeded data for customer without tw account
        mockWebServer.enqueue(response("client-credentials.json"));
        mockWebServer.enqueue(new MockResponse().setResponseCode(409));

        //when & then
        webTestClient.post()
                .uri(uriBuilder -> uriBuilder.path("/user-credentials/sign-up")
                        .queryParam("customerId", customerId)
                        .build())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString())
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    public void shouldThrowInternalServerErrorWhenSignUp() throws IOException {
        //given
        final Long customerId = 999L; //seeded data for customer without tw account
        mockWebServer.enqueue(response("client-credentials.json"));
        mockWebServer.enqueue(new MockResponse().setResponseCode(200));

        //when & then
        webTestClient.post()
                .uri(uriBuilder -> uriBuilder.path("/user-credentials/sign-up")
                        .queryParam("customerId", customerId)
                        .build())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString())
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    public void shouldLinkBankCustomerWithExistingTw() throws Exception {
        //given
        final Long customerId = 888L; //seeded customer for customer with tw account
        final String code = "c0d3";

        mockWebServer.enqueue(response("user-credentials.json"));
        mockWebServer.enqueue(response("profile.json"));
        String accessToken = "cda71663-3481-4a67-aea0-8c57d56cef24";
        String refreshToken = "5e065dbd-72fe-414b-ad56-9eaa2b45bf31";

        //when & then
        webTestClient.post()
                .uri(uriBuilder -> uriBuilder.path("/user-credentials/existing")
                        .queryParam("customerId", customerId)
                        .queryParam("code", code)
                        .build())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString())
                .exchange()
                .expectStatus().isOk();

        Thread.sleep(500); //waiting for profile to be saved async
        Connection connection = dataSource.getConnection();

        String twProfileQuery = "select * from tw_profile where customer_id = 888";
        ResultSet twProfileResult = connection.createStatement().executeQuery(twProfileQuery);
        int numberOfTwProfiles = 0;
        while (twProfileResult.next()) {
            numberOfTwProfiles++;
            assertThat(twProfileResult.getInt("tw_profile_id")).isEqualTo(246);
        }
        assertThat(numberOfTwProfiles).isEqualTo(1);

        String twUserTokensQuery = "select * from tw_user_tokens where customer_id = 888";
        ResultSet twUserTokensResult = connection.createStatement().executeQuery(twUserTokensQuery);
        int numberOfTwUserTokens = 0;
        while (twUserTokensResult.next()) {
            numberOfTwUserTokens++;
            assertThat(twUserTokensResult.getString("access_token")).isEqualTo(accessToken);
            assertThat(twUserTokensResult.getString("refresh_token")).isEqualTo(refreshToken);
        }
        assertThat(numberOfTwUserTokens).isEqualTo(1);
    }

    @Test
    public void shouldLinkBankCustomerWithExistingTwNoProfile() throws Exception {
        //given
        final Long customerId = 888L; //seeded customer for customer with tw account
        final String code = "c0d3";

        mockWebServer.enqueue(response("user-credentials.json"));
        mockWebServer.enqueue(new MockResponse().setResponseCode(200));
        mockWebServer.enqueue(response("user-credentials.json"));
        mockWebServer.enqueue(response("profile.json"));
        String accessToken = "cda71663-3481-4a67-aea0-8c57d56cef24";
        String refreshToken = "5e065dbd-72fe-414b-ad56-9eaa2b45bf31";

        //when & then
        webTestClient.post()
                .uri(uriBuilder -> uriBuilder.path("/user-credentials/existing")
                        .queryParam("customerId", customerId)
                        .queryParam("code", code)
                        .build())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString())
                .exchange()
                .expectStatus().isOk();

        Thread.sleep(500); //waiting for profile to be saved async
        Connection connection = dataSource.getConnection();

        String twProfileQuery = "select * from tw_profile where customer_id = 888";
        ResultSet twProfileResult = connection.createStatement().executeQuery(twProfileQuery);
        int numberOfTwProfiles = 0;
        while (twProfileResult.next()) {
            numberOfTwProfiles++;
            assertThat(twProfileResult.getInt("tw_profile_id")).isEqualTo(246);
        }
        assertThat(numberOfTwProfiles).isEqualTo(1);

        String twUserTokensQuery = "select * from tw_user_tokens where customer_id = 888";
        ResultSet twUserTokensResult = connection.createStatement().executeQuery(twUserTokensQuery);
        int numberOfTwUserTokens = 0;
        while (twUserTokensResult.next()) {
            numberOfTwUserTokens++;
            assertThat(twUserTokensResult.getString("access_token")).isEqualTo(accessToken);
            assertThat(twUserTokensResult.getString("refresh_token")).isEqualTo(refreshToken);
        }
        assertThat(numberOfTwUserTokens).isEqualTo(1);
    }

    @Test
    public void shouldThrowInternalServerErrorWhenExisting() throws IOException {
        //given
        final Long customerId = 888L; //seeded customer for customer with tw account
        final String code = "c0d3";

        mockWebServer.enqueue(response("user-credentials.json"));
        mockWebServer.enqueue(new MockResponse().setResponseCode(200));
        mockWebServer.enqueue(new MockResponse().setResponseCode(200));

        //when & then
        webTestClient.post()
                .uri(uriBuilder -> uriBuilder.path("/user-credentials/existing")
                        .queryParam("customerId", customerId)
                        .queryParam("code", code)
                        .build())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString())
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}