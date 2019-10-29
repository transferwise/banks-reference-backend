package com.transferwise.banks.demo.quote.web;

import com.transferwise.banks.demo.values.SourceAmount;
import com.transferwise.banks.demo.values.SourceCurrency;
import com.transferwise.banks.demo.values.TargetAmount;
import com.transferwise.banks.demo.values.TargetCurrency;

import javax.validation.constraints.NotNull;

import static java.util.Objects.nonNull;

@AmountRequired
class AnonymousQuoteRequest {

    @NotNull
    private SourceCurrency sourceCurrency;
    @NotNull
    private TargetCurrency targetCurrency;
    private SourceAmount sourceAmount;
    private TargetAmount targetAmount;

    public AnonymousQuoteRequest() {
    }

    public AnonymousQuoteRequest(final SourceCurrency sourceCurrency, final TargetCurrency targetCurrency,
                                 final SourceAmount sourceAmount, final TargetAmount targetAmount) {
        this();
        this.sourceCurrency = sourceCurrency;
        this.targetCurrency = targetCurrency;
        this.sourceAmount = sourceAmount;
        this.targetAmount = targetAmount;
    }

    public boolean isAmountPresent() {
        return nonNull(sourceAmount) || nonNull(targetAmount);
    }

    public SourceCurrency getSourceCurrency() {
        return sourceCurrency;
    }

    public TargetCurrency getTargetCurrency() {
        return targetCurrency;
    }

    public SourceAmount getSourceAmount() {
        return sourceAmount;
    }

    public TargetAmount getTargetAmount() {
        return targetAmount;
    }
}
