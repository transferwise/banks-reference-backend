package com.transferwise.banks.demo.credentials;

import com.transferwise.banks.demo.ServerTest;
import org.junit.Test;

import java.io.IOException;

import static com.transferwise.banks.demo.support.Fabricator.newCustomer;
import static org.junit.Assert.assertNotNull;

public class NewTransferWiseUserTest extends ServerTest {

    private final NewTransferWiseUser newUser = new NewTransferWiseUser(webClient, config, manager);

    @Test
    public void createAllCredentialsForNewCustomer() throws IOException {
        server.enqueue(response("client-credentials.json"));
        server.enqueue(response("user.json"));
        server.enqueue(response("user-credentials.json"));
        server.enqueue(response("profile.json"));

        final var customer = newUser.create(newCustomer()).block();
        assertNotNull(customer.getCredentials());
        assertNotNull(customer.getUser());
        assertNotNull(customer.getProfile());
    }
}
