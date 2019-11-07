package com.transferwise.banks.demo.credentials.domain;

import com.transferwise.banks.demo.ServerTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;


public class CredentialsManagerTest extends ServerTest {

    @Autowired
    private CredentialsManager credentialsManager;

    @Test
    public void createAllCredentialsForNewCustomer() throws IOException {
        //given
        final Long customerId = 999L; //seeded customer for customer without tw data
        mockWebServer.enqueue(response("client-credentials.json"));
        mockWebServer.enqueue(response("user.json"));
        mockWebServer.enqueue(response("user-credentials.json"));
        mockWebServer.enqueue(response("profile.json"));

        //when
        final var twUserTokens = credentialsManager.signUp(customerId).block();

        //then
        assertNotNull(twUserTokens);
    }

    @Test
    public void attachCredentialsToExistingCustomer() throws IOException {
        //given
        final Long customerId = 888L;
        mockWebServer.enqueue(response("user-credentials.json"));
        mockWebServer.enqueue(response("profiles.json"));

        //when
        final var twUserTokens = credentialsManager.existing(customerId, "c3p0");

        //then
        assertNotNull(twUserTokens);
    }

}