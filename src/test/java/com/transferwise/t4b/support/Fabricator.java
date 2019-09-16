package com.transferwise.t4b.support;

import com.transferwise.t4b.credentials.TransferwiseCredentials;
import com.transferwise.t4b.credentials.TransferwiseProfile;
import com.transferwise.t4b.customer.Customer;
import com.transferwise.t4b.customer.NewCustomer;
import com.transferwise.t4b.quote.PaymentOption;
import com.transferwise.t4b.quote.QuoteRequest;
import com.transferwise.t4b.values.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.transferwise.t4b.quote.PaymentOption.BANK_TRANSFER;
import static com.transferwise.t4b.quote.PaymentOption.SWIFT;
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

    final static Profile profile = new Profile(1L);
    final static SourceCurrency gbp = new SourceCurrency("GBP");
    final static TargetCurrency eur = new TargetCurrency("EUR");

    public static QuoteRequest quoteRequest() {
        final var sourceAmount = new SourceAmount(new BigDecimal(200L));
        final var targetAmount = new TargetAmount(new BigDecimal(300L));

        return quoteRequest(sourceAmount, targetAmount);
    }

    public static QuoteRequest quoteRequest(final SourceAmount s, final TargetAmount t) {
        return new QuoteRequest(profile, gbp, eur, s, t);
    }

    public static PaymentOption swift() {
        return new PaymentOption(SWIFT, BANK_TRANSFER);
    }

    public static PaymentOption bankTransfer() {
        return new PaymentOption(BANK_TRANSFER, BANK_TRANSFER);
    }
}
