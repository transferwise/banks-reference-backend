package com.transferwise.banks.demo.transfer.domain.requirements;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Group {
    private String key;
    private Integer minLength;
    private Integer maxLength;
    private String validationRegexp;

    public Group() {
    }

    public Group(String key, Integer minLength, Integer maxLength, String validationRegexp) {
        this.key = key;
        this.minLength = minLength;
        this.maxLength = maxLength;
        this.validationRegexp = validationRegexp;
    }

    public String getKey() {
        return key;
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