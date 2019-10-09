package com.transferwise.banks.demo.transfer;

import java.util.UUID;

public class TransferWiseTransferRequest {

    private Integer targetAccount;
    private UUID quoteUuid;
    private UUID customerTransactionId;
    private TransferWiseTransferDetails details;

    public TransferWiseTransferRequest(Integer targetAccount, UUID quoteUuid, UUID customerTransactionId, TransferWiseTransferDetails details) {
        this.targetAccount = targetAccount;
        this.quoteUuid = quoteUuid;
        this.customerTransactionId = customerTransactionId;
        this.details = details;
    }

    public Integer getTargetAccount() {
        return targetAccount;
    }

    public UUID getQuoteUuid() {
        return quoteUuid;
    }

    public UUID getCustomerTransactionId() {
        return customerTransactionId;
    }

    public TransferWiseTransferDetails getDetails() {
        return details;
    }
}
