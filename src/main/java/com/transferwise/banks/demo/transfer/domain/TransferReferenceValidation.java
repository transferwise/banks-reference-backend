package com.transferwise.banks.demo.transfer.domain;

public class TransferReferenceValidation {
    private Integer refereceMaxLength;
    private Integer refereceMinLength;
    private String referenceRegex;

    public TransferReferenceValidation(Integer refereceMaxLength, Integer refereceMinLength, String referenceRegex) {
        this.refereceMaxLength = refereceMaxLength;
        this.refereceMinLength = refereceMinLength;
        this.referenceRegex = referenceRegex;
    }

    public Integer getRefereceMaxLength() {
        return refereceMaxLength;
    }

    public Integer getRefereceMinLength() {
        return refereceMinLength;
    }

    public String getReferenceRegex() {
        return referenceRegex;
    }
}
