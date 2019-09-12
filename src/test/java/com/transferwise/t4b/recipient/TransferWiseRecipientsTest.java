package com.transferwise.t4b.recipient;

import com.transferwise.t4b.ServerTest;
import com.transferwise.t4b.customer.Customer;
import org.junit.Test;

import java.io.IOException;

import static com.transferwise.t4b.support.Fabricator.newCustomer;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class TransferWiseRecipientsTest extends ServerTest {

    private final TransferWiseRecipients twRecipients = new TransferWiseRecipients(webClient, manager);

    @Test
    public void findRecipientsForCustomer() throws IOException {
        final var customer = newCustomer();

        when(customers.save(any(Customer.class))).thenReturn(customer);
        server.enqueue(response("recipients.json"));

        final var recipient = twRecipients.all(customer).blockFirst();

        assertEquals("GBP", recipient.getCurrency());
        assertTrue(recipient.isActive());
    }
}
