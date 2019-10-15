package com.transferwise.banks.demo.customer;

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
import java.util.UUID;

import static java.util.Comparator.comparing;
import static javax.persistence.CascadeType.ALL;

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

    @OneToMany(cascade = ALL, mappedBy = "customerTransferId")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<CustomerTransferStatus> transferStatuses = new ArrayList<>();

    public CustomerTransfer() {
    }

    public CustomerTransfer(Long id, Long targetAccount, UUID quoteUuid, String reference, BigDecimal rate, LocalDateTime created, String sourceCurrency, BigDecimal sourceValue, String targetCurrency, BigDecimal targetValue, UUID customerTransactionId, String recipientName, BigDecimal fee, List<CustomerTransferStatus> transferStatuses) {
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
        this.transferStatuses = transferStatuses;
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

    public List<CustomerTransferStatus> getTransferStatuses() {
        transferStatuses.sort(comparing(CustomerTransferStatus::getEventTime));
        return transferStatuses;
    }
}
