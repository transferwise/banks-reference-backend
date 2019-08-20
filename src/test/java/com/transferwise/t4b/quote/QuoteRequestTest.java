package com.transferwise.t4b.quote;

import com.transferwise.t4b.values.*;
import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONCompareMode;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

public class QuoteRequestTest {

    private final Profile profile = new Profile(123L);
    private final SourceCurrency gbp = new SourceCurrency("GBP");
    private final TargetCurrency eur = new TargetCurrency("EUR");
    private final SourceAmount sourceAmount = new SourceAmount(200L);
    private final TargetAmount targetAmount = new TargetAmount(300L);

    @Test
    public void atLeastOneAmountIsRequired() {
        assertTrue(new QuoteRequest(profile, gbp, eur, sourceAmount, null).isAmountPresent());
        assertTrue(new QuoteRequest(profile, gbp, eur, null, targetAmount).isAmountPresent());

        assertFalse(new QuoteRequest(profile, gbp, eur, null, null).isAmountPresent());
    }

    @Test
    public void generateJsonRequest() throws JSONException {
        final var request = new QuoteRequest(profile, gbp, eur, sourceAmount, null);

        assertEquals("{\n" +
                "  \"sourceAmount\" : 200,\n" +
                "  \"targetCurrency\" : \"EUR\",\n" +
                "  \"sourceCurrency\" : \"GBP\",\n" +
                "  \"profile\" : 123,\n" +
                "  \"targetAmount\" : null\n" +
                "}", request.toJson(), JSONCompareMode.LENIENT);
    }
}
