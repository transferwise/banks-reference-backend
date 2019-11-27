package com.transferwise.banks.demo.quote.web;

import com.transferwise.banks.demo.values.SourceCurrency;
import com.transferwise.banks.demo.values.TargetCurrency;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

import static java.util.Objects.nonNull;

@AmountRequired
class QuoteRequest {

    @NotNull
    private SourceCurrency sourceCurrency;
    @NotNull
    private TargetCurrency targetCurrency;
    private BigDecimal sourceAmount;
    private BigDecimal targetAmount;

    public QuoteRequest() {
    }

    public QuoteRequest(final SourceCurrency sourceCurrency, final TargetCurrency targetCurrency,
                        final BigDecimal sourceAmount, final BigDecimal targetAmount) {
        this();
        this.sourceCurrency = sourceCurrency;
        this.targetCurrency = targetCurrency;
        this.sourceAmount = sourceAmount;
        this.targetAmount = targetAmount;
    }

    boolean isAmountPresent() {
        return nonNull(sourceAmount) || nonNull(targetAmount);
    }

    public SourceCurrency getSourceCurrency() {
        return sourceCurrency;
    }

    public TargetCurrency getTargetCurrency() {
        return targetCurrency;
    }

    public BigDecimal getSourceAmount() {
        return sourceAmount;
    }

    public BigDecimal getTargetAmount() {
        return targetAmount;
    }
}
