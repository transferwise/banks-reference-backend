package com.transferwise.banks.demo.transfer.persistence;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.util.Comparator.comparing;
import static javax.persistence.CascadeType.ALL;

@Entity
@Table(name = "customer_transfers")
class CustomerTransferEntity {

    @Id
    private Long id;
    private Long customerId;
    private Long recipientId;
    private String quoteId;
    private String reference;
    private BigDecimal rate;
    private LocalDateTime created;
    private String sourceCurrency;
    private BigDecimal sourceValue;
    private String targetCurrency;
    private BigDecimal targetValue;
    private String customerTransactionId;
    private String recipientName;
    private BigDecimal fee;
    private String accountSummary;

    @OneToMany(cascade = ALL, mappedBy = "customerTransferId")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<CustomerTransferStatusEntity> transferStatuses = new ArrayList<>();

    public CustomerTransferEntity() {
    }

    public CustomerTransferEntity(Long id, Long customerId, Long recipientId, String quoteId, String reference, BigDecimal rate, LocalDateTime created, String sourceCurrency, BigDecimal sourceValue, String targetCurrency, BigDecimal targetValue, String customerTransactionId, String recipientName, BigDecimal fee, String accountSummary, List<CustomerTransferStatusEntity> transferStatuses) {
        this.id = id;
        this.customerId = customerId;
        this.recipientId = recipientId;
        this.quoteId = quoteId;
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
        this.accountSummary = accountSummary;
        this.transferStatuses = transferStatuses;
    }

    public Long getId() {
        return id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public Long getRecipientId() {
        return recipientId;
    }

    public String getQuoteId() {
        return quoteId;
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

    public String getCustomerTransactionId() {
        return customerTransactionId;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public String getAccountSummary() {
        return accountSummary;
    }

    public List<CustomerTransferStatusEntity> getTransferStatuses() {
        transferStatuses.sort(comparing(CustomerTransferStatusEntity::getEventTime));
        return transferStatuses;
    }
}
