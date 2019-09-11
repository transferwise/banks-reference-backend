package com.transferwise.t4b.credentials;

import com.transferwise.t4b.ServerTest;
import org.junit.Test;

import java.io.IOException;

import static com.transferwise.t4b.Fabricator.newCustomer;
import static org.junit.Assert.assertNotNull;

public class NewTransferWiseUserTest extends ServerTest {

    private final NewTransferWiseUser request = new NewTransferWiseUser(webClient, config, manager);

    @Test
    public void createAllCredentialsForNewCustomer() throws IOException {
        server.enqueue(response("client-credentials.json"));
        server.enqueue(response("user.json"));
        server.enqueue(response("user-credentials.json"));
        server.enqueue(response("profile.json"));

        final var customer = request.create(newCustomer()).block();
        assertNotNull(customer.getCredentials());
        assertNotNull(customer.getUser());
        assertNotNull(customer.getProfile());
    }
}
