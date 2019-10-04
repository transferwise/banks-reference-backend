package com.transferwise.t4b.customer;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "customer_transfers")
public class CustomerTransfer {

    @Id
    private Long id;
    private Long targetAccount;
    private UUID quoteUuid;
    private String reference;
    private BigDecimal rate;
    private LocalDateTime created;
    private String sourceCurrency;
    private BigDecimal sourceValue;
    private String targetCurrency;
    private BigDecimal targetValue;
    private UUID customerTransactionId;
    private String recipientName;
    private BigDecimal fee;

    public CustomerTransfer() {
    }

    public CustomerTransfer(Long id, Long targetAccount, UUID quoteUuid, String reference, BigDecimal rate, LocalDateTime created, String sourceCurrency, BigDecimal sourceValue, String targetCurrency, BigDecimal targetValue, UUID customerTransactionId, String recipientName, BigDecimal fee) {
        this.id = id;
        this.targetAccount = targetAccount;
        this.quoteUuid = quoteUuid;
        this.reference = reference;
        this.rate = rate;
        this.created = created;
        this.sourceCurrency = sourceCurrency;
        this.sourceValue = sourceValue;
        this.targetCurrency = targetCurrency;
        this.targetValue = targetValue;
        this.customerTransactionId = customerTransactionId;
        this.recipientName = recipientName;
        this.fee = fee;
    }

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

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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

    public String getRecipientName() {
        return recipientName;
    }

    public BigDecimal getFee() {
        return fee;
    }
}
