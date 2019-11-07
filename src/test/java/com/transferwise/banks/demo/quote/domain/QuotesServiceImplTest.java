package com.transferwise.banks.demo.quote.domain;

import com.transferwise.banks.demo.ServerTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.math.BigDecimal;

import static org.junit.Assert.assertFalse;

public class QuotesServiceImplTest extends ServerTest {

    @Autowired
    private QuotesService quotesService;

    @Test
    public void createAnonymousQuote() throws IOException {
        //given
        CreateAnonymousQuote createAnonymousQuote = new CreateAnonymousQuote("GBP", "EUR", BigDecimal.valueOf(200L), null);
        mockWebServer.enqueue(response("quote.json"));

        //when
        final var anonymousQuote = quotesService.createAnonymousQuote(createAnonymousQuote).block();

        //then
        assertFalse(anonymousQuote.getPaymentOptions().isEmpty());

    }

    @Test
    public void createQuote() throws IOException {
        //given
        CreateQuote createQuote = new CreateQuote("GBP", "EUR", BigDecimal.valueOf(200L), null);
        mockWebServer.enqueue(response("user-credentials.json"));
        mockWebServer.enqueue(response("quote.json"));

        //when
        final var quote = quotesService.createQuote(1L, createQuote).block();

        //then
        assertFalse(quote.getPaymentOptions().isEmpty());
    }

}