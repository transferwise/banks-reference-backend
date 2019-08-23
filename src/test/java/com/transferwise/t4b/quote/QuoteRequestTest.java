package com.transferwise.t4b.quote;

import com.transferwise.t4b.values.*;
import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.io.IOException;
import java.math.BigDecimal;

import static com.transferwise.t4b.support.FileReader.read;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

public class QuoteRequestTest {

    private final Profile profile = new Profile(1L);
    private final SourceCurrency gbp = new SourceCurrency("GBP");
    private final TargetCurrency eur = new TargetCurrency("EUR");
    private final SourceAmount sourceAmount = new SourceAmount(new BigDecimal(200L));
    private final TargetAmount targetAmount = new TargetAmount(new BigDecimal(300L));

    @Test
    public void atLeastOneAmountIsRequired() {
        assertTrue(quote(sourceAmount, null).isAmountPresent());
        assertTrue(quote(null, targetAmount).isAmountPresent());

        assertFalse(quote(null, null).isAmountPresent());
    }

    @Test
    public void generateJsonRequest() throws JSONException, IOException {
        final var expected = read("valid-quote-request.json");
        final var request = quote(sourceAmount, null);

        assertEquals(expected, request.toJson(), JSONCompareMode.LENIENT);
    }

    private QuoteRequest quote(final SourceAmount s, final TargetAmount t) {
        return new QuoteRequest(profile, gbp, eur, s, t);
    }
}
