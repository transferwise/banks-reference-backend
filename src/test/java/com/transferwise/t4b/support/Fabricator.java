package com.transferwise.t4b.support;

import com.transferwise.t4b.credentials.TransferwiseCredentials;
import com.transferwise.t4b.credentials.TransferwiseProfile;
import com.transferwise.t4b.customer.Customer;
import com.transferwise.t4b.customer.NewCustomer;
import com.transferwise.t4b.quote.QuoteRequest;
import com.transferwise.t4b.values.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import static java.time.ZonedDateTime.now;

public class Fabricator {

    public static Customer newCustomer() {
        final var credentials = new TransferwiseCredentials("", "", 1);
        final var profile = new TransferwiseProfile(1L, "personal", now());
        final var dob = LocalDate.of(1989, 5, 16);

        return new Customer(new NewCustomer("Fulano", "de Tal", "fulano@email.com", dob))
                .withCredentials(credentials)
                .withPersonalProfile(profile);
    }

    public static QuoteRequest newQuoteRequest() {
        final var profile = new Profile(1L);
        final var gbp = new SourceCurrency("GBP");
        final var eur = new TargetCurrency("EUR");
        final var sourceAmount = new SourceAmount(new BigDecimal(200L));
        final var targetAmount = new TargetAmount(new BigDecimal(300L));

        return new QuoteRequest(profile, gbp, eur, sourceAmount, targetAmount);
    }
}
