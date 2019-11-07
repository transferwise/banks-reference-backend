package com.transferwise.banks.demo.transfer.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.transferwise.banks.demo.transfer.domain.status.CustomerTransferStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.util.Comparator.comparing;

public class CustomerTransfer {

    private Long id;
    private Long customerId;
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
    private String recipientName;
    private BigDecimal fee;

    private List<CustomerTransferStatus> transferStatuses = new ArrayList<>();

    public CustomerTransfer(Long id, Long customerId, Long targetAccount, UUID quoteUuid, String reference, BigDecimal rate, LocalDateTime created, String sourceCurrency, BigDecimal sourceValue, String targetCurrency, BigDecimal targetValue, UUID customerTransactionId, String recipientName, BigDecimal fee, List<CustomerTransferStatus> transferStatuses) {
        this.id = id;
        this.customerId = customerId;
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
        this.transferStatuses = transferStatuses;
    }

    public Long getId() {
        return id;
    }

    public Long getCustomerId() {
        return customerId;
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

    public String getRecipientName() {
        return recipientName;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public List<CustomerTransferStatus> getTransferStatuses() {
        transferStatuses.sort(comparing(CustomerTransferStatus::getEventTime));
        return transferStatuses;
    }
}
