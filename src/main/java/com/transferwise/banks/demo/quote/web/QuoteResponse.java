package com.transferwise.banks.demo.quote.web;

import java.math.BigDecimal;
import java.util.UUID;

class QuoteResponse {

    private UUID id;
    private String sourceCurrency;
    private String targetCurrency;
    private BigDecimal sourceAmount;
    private BigDecimal targetAmount;
    private BigDecimal rate;
    private BigDecimal fee;
    private String formattedEstimatedDelivery;

    public QuoteResponse(UUID id, String sourceCurrency, String targetCurrency, BigDecimal sourceAmount, BigDecimal targetAmount, BigDecimal rate, BigDecimal fee, String formattedEstimatedDelivery) {
        this.id = id;
        this.sourceCurrency = sourceCurrency;
        this.targetCurrency = targetCurrency;
        this.sourceAmount = sourceAmount;
        this.targetAmount = targetAmount;
        this.rate = rate;
        this.fee = fee;
        this.formattedEstimatedDelivery = formattedEstimatedDelivery;
    }

    public UUID getId() {
        return id;
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

    public BigDecimal getRate() {
        return rate;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public String getFormattedEstimatedDelivery() {
        return formattedEstimatedDelivery;
    }
}
