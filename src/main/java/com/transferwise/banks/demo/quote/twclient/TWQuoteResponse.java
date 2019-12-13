package com.transferwise.banks.demo.quote.twclient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.transferwise.banks.demo.quote.domain.PaymentOption;
import com.transferwise.banks.demo.quote.domain.QuoteNotice;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.util.Collections.unmodifiableList;

class TWQuoteResponse {

    private UUID id;
    private String sourceCurrency;
    private String targetCurrency;
    private BigDecimal sourceAmount;
    private BigDecimal targetAmount;
    private String payOut;
    private BigDecimal rate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime createdTime;
    private BigDecimal fee;
    private Integer user;
    private Integer profile;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime rateExpirationTime;
    private String providedAmountType;
    private String status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime expirationTime;

    private final List<QuoteNotice> notices = new ArrayList<>();
    private final List<PaymentOption> paymentOptions;

    public TWQuoteResponse() {
        this(new ArrayList<>());
    }

    public TWQuoteResponse(final List<PaymentOption> paymentOptions) {
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

    public List<QuoteNotice> getNotices() {
        return unmodifiableList(notices);
    }

    public List<PaymentOption> getPaymentOptions() {
        return unmodifiableList(paymentOptions);
    }
}