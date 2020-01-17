package com.transferwise.banks.demo.transfer.domain;

public class TransferRequestDetails {

    private String reference;
    private String transferPurpose;
    private String sourceOfFunds;

    public TransferRequestDetails() {
    }

    public TransferRequestDetails(String reference, String transferPurpose, String sourceOfFunds) {
        this.reference = reference;
        this.transferPurpose = transferPurpose;
        this.sourceOfFunds = sourceOfFunds;
    }

    public String getReference() {
        return reference;
    }

    public String getTransferPurpose() {
        return transferPurpose;
    }

    public String getSourceOfFunds() {
        return sourceOfFunds;
    }
}
