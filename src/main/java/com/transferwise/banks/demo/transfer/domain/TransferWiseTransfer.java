package com.transferwise.banks.demo.transfer.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class TransferWiseTransfer {

    private Long id;
    private Long targetAccount;
    private UUID quoteUuid;
    private String reference;
    private BigDecimal rate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;
    private String sourceCurrency;
    private BigDecimal sourceValue;
    private String targetCurrency;
    private BigDecimal targetValue;
    private UUID customerTransactionId;

    public Long getId() {
        return id;
    }

    public Long getTargetAccount() {
        return targetAccount;
    }

    public UUID getQuoteUuid() {
        return quoteUuid;
    }

    public String getReference() {
        return reference;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public String getSourceCurrency() {
        return sourceCurrency;
    }

    public BigDecimal getSourceValue() {
        return sourceValue;
    }

    public String getTargetCurrency() {
        return targetCurrency;
    }

    public BigDecimal getTargetValue() {
        return targetValue;
    }

    public UUID getCustomerTransactionId() {
        return customerTransactionId;
    }
}
