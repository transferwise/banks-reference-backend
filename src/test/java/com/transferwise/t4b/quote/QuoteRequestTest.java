package com.transferwise.t4b.quote;

import com.transferwise.t4b.values.SourceAmount;
import com.transferwise.t4b.values.TargetAmount;
import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.io.IOException;
import java.math.BigDecimal;

import static com.transferwise.t4b.support.Fabricator.quoteRequest;
import static com.transferwise.t4b.support.FileReader.read;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

public class QuoteRequestTest {

    private final SourceAmount sourceAmount = new SourceAmount(new BigDecimal(200L));
    private final TargetAmount targetAmount = new TargetAmount(new BigDecimal(300L));

    @Test
    public void atLeastOneAmountIsRequired() {
        assertTrue(quoteRequest(sourceAmount, null).isAmountPresent());
        assertTrue(quoteRequest(null, targetAmount).isAmountPresent());

        assertFalse(quoteRequest(null, null).isAmountPresent());
    }

    @Test
    public void generateJsonRequest() throws JSONException, IOException {
        final var expected = read("valid-quote-request.json");
        final var request = quoteRequest(sourceAmount, null);

        assertEquals(expected, request.toJson(), JSONCompareMode.LENIENT);
    }
}
