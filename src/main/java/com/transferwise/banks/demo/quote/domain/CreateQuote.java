package com.transferwise.banks.demo.quote.domain;

import java.math.BigDecimal;

public class CreateQuote {

    private Long profileId;
    private String sourceCurrency;
    private String targetCurrency;
    private BigDecimal sourceAmount;
    private BigDecimal targetAmount;

    public CreateQuote(Long profileId, String sourceCurrency, String targetCurrency, BigDecimal sourceAmount, BigDecimal targetAmount) {
        this.profileId = profileId;
        this.sourceCurrency = sourceCurrency;
        this.targetCurrency = targetCurrency;
        this.sourceAmount = sourceAmount;
        this.targetAmount = targetAmount;
    }

    public Long getProfileId() {
        return profileId;
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
}
