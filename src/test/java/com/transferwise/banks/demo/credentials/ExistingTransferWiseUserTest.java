package com.transferwise.banks.demo.credentials;

import com.transferwise.banks.demo.ServerTest;
import com.transferwise.banks.demo.client.params.Code;
import org.junit.Test;

import java.io.IOException;

import static com.transferwise.banks.demo.support.Fabricator.newCustomer;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class ExistingTransferWiseUserTest extends ServerTest {

    private final ExistingTransferWiseUser existingUser = new ExistingTransferWiseUser(webClient, config, manager);

    @Test
    public void attachCredentialsToExistingCustomer() throws IOException {
        server.enqueue(response("client-credentials.json"));
        server.enqueue(response("profiles.json"));

        final var customer = existingUser.attach(new Code("c3p0"), newCustomer()).block();
        assertNotNull(customer.getCredentials());
        assertNull(customer.getUser());
        assertNotNull(customer.getProfile());
    }
}
