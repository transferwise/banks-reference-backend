package com.transferwise.t4b.client;

import com.transferwise.t4b.ServerTest;
import com.transferwise.t4b.customer.Customer;
import org.junit.Test;

import java.io.IOException;

import static com.transferwise.t4b.Fabricator.newCustomer;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ApiClientTest extends ServerTest {

    private final ApiClient client = new ApiClient(webClient, config, manager);

    @Test
    public void findRecipientsForCustomer() throws IOException {
        final var customer = newCustomer();

        when(customers.save(any(Customer.class))).thenReturn(customer);
        server.enqueue(response("recipients.json"));

        final var recipient = client.recipients(customer).blockFirst();

        assertEquals("GBP", recipient.getCurrency());
        assertTrue(recipient.isActive());
    }
}
