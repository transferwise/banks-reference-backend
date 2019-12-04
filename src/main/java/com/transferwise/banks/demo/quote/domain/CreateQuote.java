package com.transferwise.banks.demo.quote.domain;

import java.math.BigDecimal;

public class CreateQuote {

    private Long profile;
    private String sourceCurrency;
    private String targetCurrency;
    private BigDecimal sourceAmount;
    private BigDecimal targetAmount;

    public CreateQuote(String sourceCurrency, String targetCurrency, BigDecimal sourceAmount, BigDecimal targetAmount) {
        this.sourceCurrency = sourceCurrency;
        this.targetCurrency = targetCurrency;
        this.sourceAmount = sourceAmount;
        this.targetAmount = targetAmount;
    }

    public Long getProfile() {
        return profile;
    }

    public String getSourceCurrency() {
        return sourceCurrency;
    }

    public String getTargetCurrency() {
        return targetCurrency;
    }

    public BigDecimal getSourceAmount() {
        return sourceAmount;
    }

    public BigDecimal getTargetAmount() {
        return targetAmount;
    }

    public CreateQuote withProfile(final Long profile) {
        this.profile = profile;
        return this;
    }
}
