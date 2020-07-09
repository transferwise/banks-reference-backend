package com.transferwise.banks.demo.quote.web;

<<<<<<< HEAD
import java.math.BigDecimal;
=======
import com.transferwise.banks.demo.quote.domain.QuoteNotice;

import java.math.BigDecimal;
import java.util.List;
>>>>>>> 80005fdfa15e98a1dde8987b902e5886f92589cc
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
<<<<<<< HEAD

    public QuoteResponse(UUID id, String sourceCurrency, String targetCurrency, BigDecimal sourceAmount, BigDecimal targetAmount, BigDecimal rate, BigDecimal fee, String formattedEstimatedDelivery) {
=======
    private String rateType;
    private List<QuoteNotice> notices;

    public QuoteResponse(UUID id, String sourceCurrency, String targetCurrency, BigDecimal sourceAmount, BigDecimal targetAmount, BigDecimal rate, BigDecimal fee, String formattedEstimatedDelivery, String rateType, List<QuoteNotice> notices) {
>>>>>>> 80005fdfa15e98a1dde8987b902e5886f92589cc
        this.id = id;
        this.sourceCurrency = sourceCurrency;
        this.targetCurrency = targetCurrency;
        this.sourceAmount = sourceAmount;
        this.targetAmount = targetAmount;
        this.rate = rate;
        this.fee = fee;
        this.formattedEstimatedDelivery = formattedEstimatedDelivery;
<<<<<<< HEAD
=======
        this.rateType = rateType;
        this.notices = notices;
>>>>>>> 80005fdfa15e98a1dde8987b902e5886f92589cc
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
<<<<<<< HEAD
=======

    public String getRateType() {
        return rateType;
    }

    public List<QuoteNotice> getNotices() {
        return notices;
    }
>>>>>>> 80005fdfa15e98a1dde8987b902e5886f92589cc
}
