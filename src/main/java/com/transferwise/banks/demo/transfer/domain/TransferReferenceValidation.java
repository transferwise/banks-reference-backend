package com.transferwise.banks.demo.transfer.domain;

public class TransferReferenceValidation {
    private Integer minLength;
    private Integer maxLength;
    private String validationRegexp;

    public TransferReferenceValidation(Integer minLength, Integer maxLength, String validationRegexp) {
        this.minLength = minLength;
        this.maxLength = maxLength;
        this.validationRegexp = validationRegexp;
    }

    public Integer getMinLength() {
        return minLength;
    }

    public Integer getMaxLength() {
        return maxLength;
    }

    public String getValidationRegexp() {
        return validationRegexp;
    }
}
