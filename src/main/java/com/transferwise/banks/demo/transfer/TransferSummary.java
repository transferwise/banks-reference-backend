package com.transferwise.banks.demo.transfer;

import com.transferwise.banks.demo.quote.BestPaymentOption;
import com.transferwise.banks.demo.quote.PaymentOption;
import com.transferwise.banks.demo.quote.Quote;
import com.transferwise.banks.demo.recipient.Recipient;

import java.math.BigDecimal;

public class TransferSummary {

    private final PaymentOption paymentOption;
    private final Quote quote;
    private final Recipient recipient;

    TransferSummary(final Quote quote, final Recipient recipient) {
        paymentOption = new BestPaymentOption(quote, recipient.getType()).get();
        this.quote = quote;
        this.recipient = recipient;
    }

    public String getSourceCurrency() {
        return quote.getSourceCurrency();
    }

    public String getTargetCurrency() {
        return quote.getTargetCurrency();
    }

    public BigDecimal getRate() {
        return quote.getRate();
    }

    public String recipientName() {
        return recipient.getName().getFullName();
    }

    public String getAccountSummary() {
        return recipient.getAccountSummary();
    }

    public PaymentOption getPaymentOption() {
        return paymentOption;
    }
}
