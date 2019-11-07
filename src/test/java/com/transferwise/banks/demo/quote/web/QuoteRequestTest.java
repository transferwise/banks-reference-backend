package com.transferwise.banks.demo.quote.web;

import com.transferwise.banks.demo.values.Profile;
import com.transferwise.banks.demo.values.SourceAmount;
import com.transferwise.banks.demo.values.SourceCurrency;
import com.transferwise.banks.demo.values.TargetAmount;
import com.transferwise.banks.demo.values.TargetCurrency;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class QuoteRequestTest {
    private final static Profile PROFILE = new Profile(1L);
    private final static SourceCurrency GBP = new SourceCurrency("GBP");
    private final static TargetCurrency EUR = new TargetCurrency("EUR");
    private final static SourceAmount SOURCE_AMOUNT = new SourceAmount(new BigDecimal(200L));
    private final static TargetAmount TARGET_AMOUNT = new TargetAmount(new BigDecimal(300L));

    @Test
    public void atLeastOneAmountIsRequired() {
        assertTrue(quoteRequest(SOURCE_AMOUNT, null).isAmountPresent());
        assertTrue(quoteRequest( null, TARGET_AMOUNT).isAmountPresent());

        assertFalse(quoteRequest( null, null).isAmountPresent());
    }

    private static QuoteRequest quoteRequest(final SourceAmount s, final TargetAmount t) {
        return new QuoteRequest(PROFILE, GBP, EUR, s, t);
    }

}