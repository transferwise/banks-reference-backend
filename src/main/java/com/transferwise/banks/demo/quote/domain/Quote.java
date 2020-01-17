package com.transferwise.banks.demo.quote.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    private LocalDateTime rateExpirationTime;
    private String providedAmountType;
    private String status;

    private LocalDateTime expirationTime;

    private final List<QuoteNotice> notices = new ArrayList<>();
    private final List<PaymentOption> paymentOptions;
    private String formattedEstimatedDelivery;

    public Quote(UUID id, String sourceCurrency, String targetCurrency, BigDecimal sourceAmount, BigDecimal targetAmount, String payOut, BigDecimal rate, LocalDateTime createdTime, BigDecimal fee, Integer user, Integer profile, LocalDateTime rateExpirationTime, String providedAmountType, String status, LocalDateTime expirationTime, List<PaymentOption> paymentOptions) {
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
        this.rateExpirationTime = rateExpirationTime;
        this.providedAmountType = providedAmountType;
        this.status = status;
        this.expirationTime = expirationTime;
        this.paymentOptions = paymentOptions;
    }

    public Quote() {
        this(new ArrayList<>());
    }

    public Quote(final List<PaymentOption> paymentOptions) {
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