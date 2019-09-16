package com.transferwise.t4b.quote;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class BestPaymentOption {

    private final List<PaymentOption> options;
    private final Supplier<Optional<PaymentOption>> supplier;

    public BestPaymentOption(final Quote quote, final String recipientType) {
        options = quote.getPaymentOptions();
        supplier = supplierFor(recipientType);
    }

    private Supplier<Optional<PaymentOption>> supplierFor(final String recipientType) {
        if (recipientType.equals("SWIFT")) {
            return swift();
        }

        return bankTransfer();
    }

    public PaymentOption get() {
        return supplier.get().orElseThrow();
    }

    private Supplier<Optional<PaymentOption>> swift() {
        return () -> options
                .stream()
                .filter(PaymentOption::isSwift)
                .findFirst()
                .or(bankTransfer());
    }

    private Supplier<Optional<PaymentOption>> bankTransfer() {
        return () -> options
                .stream()
                .filter(PaymentOption::isBankTransfer)
                .findFirst();
    }
}
