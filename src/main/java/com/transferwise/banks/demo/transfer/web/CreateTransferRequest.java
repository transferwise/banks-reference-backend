package com.transferwise.banks.demo.transfer.web;

import com.transferwise.banks.demo.transfer.domain.TransferRequestDetails;

import java.util.UUID;

class CreateTransferRequest {
    private Long recipientId;
    private UUID quoteId;
    private TransferRequestDetails details;

    public CreateTransferRequest() {
    }

    public CreateTransferRequest(Long recipientId, UUID quoteId, TransferRequestDetails details) {
        this.recipientId = recipientId;
        this.quoteId = quoteId;
        this.details = details;
    }

    public Long getRecipientId() {
        return recipientId;
    }

    public UUID getQuoteId() {
        return quoteId;
    }

    public TransferRequestDetails getDetails() {
        return details;
    }
}
