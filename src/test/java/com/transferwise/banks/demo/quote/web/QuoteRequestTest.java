package com.transferwise.banks.demo.quote.web;

import com.transferwise.banks.demo.values.SourceCurrency;
import com.transferwise.banks.demo.values.TargetCurrency;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class QuoteRequestTest {
    private final static SourceCurrency GBP = new SourceCurrency("GBP");
    private final static TargetCurrency EUR = new TargetCurrency("EUR");
    private final static BigDecimal SOURCE_AMOUNT = new BigDecimal(200L);
    private final static BigDecimal TARGET_AMOUNT = new BigDecimal(300L);

    @Test
    public void atLeastOneAmountIsRequired() {
        assertTrue(quoteRequest(SOURCE_AMOUNT, null).isAmountPresent());
        assertTrue(quoteRequest(null, TARGET_AMOUNT).isAmountPresent());

        assertFalse(quoteRequest(null, null).isAmountPresent());
    }

    private static QuoteRequest quoteRequest(final BigDecimal s, final BigDecimal t) {
        return new QuoteRequest(GBP, EUR, s, t);
    }

}