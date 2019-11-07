package com.transferwise.banks.demo.transfer.domain;

import java.util.UUID;

public class TransferRequest {

    private Long targetAccount;
    private UUID quoteUuid;
    private UUID customerTransactionId;
    private TransferRequestDetails details;

    public TransferRequest() {
    }

    public TransferRequest(Long targetAccount, UUID quoteUuid, UUID customerTransactionId, TransferRequestDetails details) {
        this.targetAccount = targetAccount;
        this.quoteUuid = quoteUuid;
        this.customerTransactionId = customerTransactionId;
        this.details = details;
    }

    public Long getTargetAccount() {
        return targetAccount;
    }

    public UUID getQuoteUuid() {
        return quoteUuid;
    }

    public UUID getCustomerTransactionId() {
        return customerTransactionId;
    }

    public TransferRequestDetails getDetails() {
        return details;
    }
}
