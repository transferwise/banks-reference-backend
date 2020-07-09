package com.transferwise.banks.demo.quote.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
<<<<<<< HEAD
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
=======
import java.util.*;
>>>>>>> 80005fdfa15e98a1dde8987b902e5886f92589cc

import static java.util.Collections.unmodifiableList;

public class Quote {

    private static final String BANK_TRANSFER = "BANK_TRANSFER";

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

<<<<<<< HEAD
=======
    private String rateType;
>>>>>>> 80005fdfa15e98a1dde8987b902e5886f92589cc
    private LocalDateTime rateExpirationTime;
    private String providedAmountType;
    private String status;

    private LocalDateTime expirationTime;

<<<<<<< HEAD
    private final List<QuoteNotice> notices = new ArrayList<>();
    private final List<PaymentOption> paymentOptions;
    private String formattedEstimatedDelivery;

    public Quote(UUID id, String sourceCurrency, String targetCurrency, BigDecimal sourceAmount, BigDecimal targetAmount, String payOut, BigDecimal rate, LocalDateTime createdTime, BigDecimal fee, Integer user, Integer profile, LocalDateTime rateExpirationTime, String providedAmountType, String status, LocalDateTime expirationTime, List<PaymentOption> paymentOptions) {
=======
    private final List<QuoteNotice> notices;
    private final List<PaymentOption> paymentOptions;
    private String formattedEstimatedDelivery;

    public Quote(UUID id, String sourceCurrency, String targetCurrency, BigDecimal sourceAmount, BigDecimal targetAmount, String payOut, BigDecimal rate, LocalDateTime createdTime, BigDecimal fee, Integer user, Integer profile, String rateType, LocalDateTime rateExpirationTime, String providedAmountType, String status, LocalDateTime expirationTime, List<QuoteNotice> notices, List<PaymentOption> paymentOptions) {
>>>>>>> 80005fdfa15e98a1dde8987b902e5886f92589cc
        this.id = id;
        this.sourceCurrency = sourceCurrency;
        this.targetCurrency = targetCurrency;
        this.sourceAmount = sourceAmount;
        this.targetAmount = targetAmount;
        this.payOut = payOut;
        this.rate = rate;
        this.createdTime = createdTime;
        this.fee = fee;
        this.user = user;
        this.profile = profile;
<<<<<<< HEAD
=======
        this.rateType = rateType;
>>>>>>> 80005fdfa15e98a1dde8987b902e5886f92589cc
        this.rateExpirationTime = rateExpirationTime;
        this.providedAmountType = providedAmountType;
        this.status = status;
        this.expirationTime = expirationTime;
<<<<<<< HEAD
=======
        this.notices = notices;
>>>>>>> 80005fdfa15e98a1dde8987b902e5886f92589cc
        this.paymentOptions = paymentOptions;
    }

    public Quote() {
        this(new ArrayList<>());
    }

    public Quote(final List<PaymentOption> paymentOptions) {
        this.paymentOptions = paymentOptions;
<<<<<<< HEAD
=======
        this.notices = new ArrayList<>();
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

<<<<<<< HEAD
=======
    public String getRateType() {
        return rateType;
    }

>>>>>>> 80005fdfa15e98a1dde8987b902e5886f92589cc
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

    public String getFormattedEstimatedDelivery() {
        return formattedEstimatedDelivery;
    }

    public Quote withFee(BigDecimal fee) {
        this.fee = fee;
        return this;
    }

    public Quote withSourceAmount(BigDecimal sourceAmount) {
        this.sourceAmount = sourceAmount;
        return this;
    }

    public Quote withTargetAmount(BigDecimal targetAmount) {
        this.targetAmount = targetAmount;
        return this;
    }

    public Quote withFormattedEstimatedDelivery(String formattedEstimatedDelivery) {
        this.formattedEstimatedDelivery = formattedEstimatedDelivery;
        return this;
    }

    public Optional<PaymentOption> extractCorrectPaymentOption() {
        return this.paymentOptions
                .stream()
                .filter(paymentOption ->
                        BANK_TRANSFER.equalsIgnoreCase(paymentOption.getPayIn())
                                && this.payOut.equalsIgnoreCase(paymentOption.getPayOut()))
                .findFirst();
    }
}