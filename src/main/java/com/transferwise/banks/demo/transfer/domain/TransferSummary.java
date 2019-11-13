package com.transferwise.banks.demo.transfer.domain;

import java.math.BigDecimal;
import java.util.UUID;

public class TransferSummary {

    private UUID quoteId;
    private Long recipientId;
    private String sourceCurrency;
    private String targetCurrency;
    private BigDecimal sourceAmount;
    private BigDecimal targetAmount;
    private BigDecimal rate;
    private BigDecimal fee;
    private String recipientName;
    private String accountSummary;
    private String formattedEstimatedDelivery;

    public TransferSummary(UUID quoteId, Long recipientId, String sourceCurrency, String targetCurrency, BigDecimal sourceAmount, BigDecimal targetAmount, BigDecimal rate, BigDecimal fee, String recipientName, String accountSummary, String formattedEstimatedDelivery) {
        this.quoteId = quoteId;
        this.recipientId = recipientId;
        this.sourceCurrency = sourceCurrency;
        this.targetCurrency = targetCurrency;
        this.sourceAmount = sourceAmount;
        this.targetAmount = targetAmount;
        this.rate = rate;
        this.fee = fee;
        this.recipientName = recipientName;
        this.accountSummary = accountSummary;
        this.formattedEstimatedDelivery = formattedEstimatedDelivery;
    }

    public UUID getQuoteId() {
        return quoteId;
    }

    public Long getRecipientId() {
        return recipientId;
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

    public String getRecipientName() {
        return recipientName;
    }

    public String getAccountSummary() {
        return accountSummary;
    }

    public String getFormattedEstimatedDelivery() {
        return formattedEstimatedDelivery;
    }
}
