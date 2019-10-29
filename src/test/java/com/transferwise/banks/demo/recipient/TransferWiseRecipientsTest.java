package com.transferwise.banks.demo.recipient;

import com.transferwise.banks.demo.ServerTest;
import com.transferwise.banks.demo.customer.persistence.CustomerEntity;
import org.junit.Test;

import java.io.IOException;

import static com.transferwise.banks.demo.support.Fabricator.newCustomer;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class TransferWiseRecipientsTest extends ServerTest {

    private final TransferWiseRecipients recipients = new TransferWiseRecipients(webClient, manager);

    @Test
    public void findRecipientsForCustomer() throws IOException {
        final var customer = newCustomer();

        when(customers.save(any(CustomerEntity.class))).thenReturn(customer);
        server.enqueue(response("recipients.json"));

        final var recipient = recipients.all(customer).blockFirst();

        assertEquals("GBP", recipient.getCurrency());
        assertTrue(recipient.isActive());
    }
}
