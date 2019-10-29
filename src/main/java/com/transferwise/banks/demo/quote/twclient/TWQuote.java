package com.transferwise.banks.demo.quote.twclient;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.util.Collections.unmodifiableList;

class TWQuote {

    private UUID id;
    private String sourceCurrency;
    private String targetCurrency;
    private BigDecimal sourceAmount;
    private BigDecimal targetAmount;
    private String payOut;
    private BigDecimal rate;
    private LocalDateTime createdTime;
    private BigDecimal fee;
    private Integer user;
    private Integer profile;
    private LocalDateTime rateExpirationTime;
    private String providedAmountType;
    private String status;
    private LocalDateTime expirationTime;

    private final List<TWQuoteNotice> notices = new ArrayList<>();
    private final List<TWPaymentOption> paymentOptions;

    public TWQuote() {
        this(new ArrayList<>());
    }

    public TWQuote(final List<TWPaymentOption> paymentOptions) {
        this.paymentOptions = paymentOptions;
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

    public String getPayOut() {
        return payOut;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public Integer getUser() {
        return user;
    }

    public Integer getProfile() {
        return profile;
    }

    public LocalDateTime getRateExpirationTime() {
        return rateExpirationTime;
    }

    public String getProvidedAmountType() {
        return providedAmountType;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getExpirationTime() {
        return expirationTime;
    }

    public List<TWQuoteNotice> getNotices() {
        return unmodifiableList(notices);
    }

    public List<TWPaymentOption> getPaymentOptions() {
        return unmodifiableList(paymentOptions);
    }
}