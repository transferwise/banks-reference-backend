package com.transferwise.banks.demo.support;

import com.transferwise.banks.demo.credentials.TransferwiseCredentials;
import com.transferwise.banks.demo.credentials.TransferwiseProfile;
import com.transferwise.banks.demo.customer.Customer;
import com.transferwise.banks.demo.customer.NewCustomer;
import com.transferwise.banks.demo.quote.PaymentOption;
import com.transferwise.banks.demo.quote.QuoteRequest;
import com.transferwise.banks.demo.values.Profile;
import com.transferwise.banks.demo.values.SourceAmount;
import com.transferwise.banks.demo.values.SourceCurrency;
import com.transferwise.banks.demo.values.TargetAmount;
import com.transferwise.banks.demo.values.TargetCurrency;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.transferwise.banks.demo.quote.PaymentOption.BANK_TRANSFER;
import static com.transferwise.banks.demo.quote.PaymentOption.SWIFT;
import static java.time.ZonedDateTime.now;

public class Fabricator {

    public static Customer newCustomer() {
        final var credentials = new TransferwiseCredentials("", "", 1);
        final var profile = new TransferwiseProfile(1L, "personal", now());
        final var dob = LocalDate.of(1989, 5, 16);

        return new Customer(new NewCustomer("Fulano", "de Tal", "fulano@email.com", dob, "+37211223344"))
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
