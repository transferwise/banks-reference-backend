package com.transferwise.banks.demo.transfer.web;

import com.transferwise.banks.demo.transfer.domain.TransferRequestDetails;

import java.util.UUID;

class CreateTransferRequest {
    private Long targetAccount;
    private UUID quoteUuid;
    private TransferRequestDetails details;

    public CreateTransferRequest() {
    }

    public CreateTransferRequest(Long targetAccount, UUID quoteUuid, TransferRequestDetails details) {
        this.targetAccount = targetAccount;
        this.quoteUuid = quoteUuid;
        this.details = details;
    }

    public Long getTargetAccount() {
        return targetAccount;
    }

    public UUID getQuoteUuid() {
        return quoteUuid;
    }

    public TransferRequestDetails getDetails() {
        return details;
    }
}
