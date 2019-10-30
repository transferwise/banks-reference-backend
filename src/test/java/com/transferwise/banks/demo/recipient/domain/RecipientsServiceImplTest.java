package com.transferwise.banks.demo.recipient.domain;

import com.transferwise.banks.demo.ServerTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

import static org.junit.Assert.*;

public class RecipientsServiceImplTest extends ServerTest {

    @Autowired
    private RecipientsService recipientsService;

    @Test
    public void findRecipientsForCustomer() throws IOException {
        //given
        final Long customerId = 1L;
        mockWebServer.enqueue(response("user-credentials.json"));
        mockWebServer.enqueue(response("recipients.json"));

        //when
        final var recipient = recipientsService.getAllRecipients(customerId).blockFirst();

        //then
        assertEquals("GBP", recipient.getCurrency());
        assertTrue(recipient.isActive());
    }

}