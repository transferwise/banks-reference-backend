package com.transferwise.banks.demo.quote;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@Component
public class PaymentOptionService {

    private static final String SWIFT = "SWIFT";

    public PaymentOption getBestPaymentOption(final Quote quote, final String recipientType) {
        return calculateBestPaymentOption(quote.getPaymentOptions(), recipientType)
                .get()
                .orElseThrow();
    }

    private Supplier<Optional<PaymentOption>> calculateBestPaymentOption(final List<PaymentOption> paymentOptions, final String recipientType) {
        if (SWIFT.equals(recipientType)) {
            return swift(paymentOptions);
        }

        return bankTransfer(paymentOptions);
    }


    private Supplier<Optional<PaymentOption>> swift(final List<PaymentOption> options) {
        return () -> options
                .stream()
                .filter(PaymentOption::isSwift)
                .findFirst()
                .or(bankTransfer(options));
    }

    private Supplier<Optional<PaymentOption>> bankTransfer(final List<PaymentOption> options) {
        return () -> options
                .stream()
                .filter(PaymentOption::isBankTransfer)
                .findFirst();
    }
}
