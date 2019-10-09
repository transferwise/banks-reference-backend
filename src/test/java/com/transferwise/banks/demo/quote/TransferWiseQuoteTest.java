package com.transferwise.banks.demo.quote;

import com.transferwise.banks.demo.ServerTest;
import org.junit.Test;

import java.io.IOException;

import static com.transferwise.banks.demo.support.Fabricator.newCustomer;
import static com.transferwise.banks.demo.support.Fabricator.quoteRequest;
import static org.junit.Assert.assertFalse;

public class TransferWiseQuoteTest extends ServerTest {

    private final TransferWiseQuote twQuote = new TransferWiseQuote(webClient, manager);

    @Test
    public void createAnonymousQuote() throws IOException {
        server.enqueue(response("quote.json"));

        final var quote = twQuote.createAnonymous(quoteRequest()).block();
        assertFalse(quote.getPaymentOptions().isEmpty());
    }

    @Test
    public void createQuote() throws IOException {
        server.enqueue(response("quote.json"));

        final var quote = twQuote.create(newCustomer(), quoteRequest()).block();
        assertFalse(quote.getPaymentOptions().isEmpty());
    }
}
