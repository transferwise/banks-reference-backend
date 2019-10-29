package com.transferwise.banks.demo.transfer.domain;

import com.transferwise.banks.demo.quote.Quote;
import com.transferwise.banks.demo.recipient.domain.Recipient;

import java.math.BigDecimal;

public class TransferSummary {

    private final Quote quote;
    private final Recipient recipient;

    public TransferSummary(final Quote quote, final Recipient recipient) {
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

    public String getPayOut() {
        return quote.getPayOut();
    }

}
