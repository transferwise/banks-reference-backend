package com.transferwise.banks.demo.transfer.domain.requirements;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TransferRequirement {

    private String type;
    private List<Field> fields = null;

    public TransferRequirement() {
    }

    public TransferRequirement(String type, List<Field> fields) {
        this.type = type;
        this.fields = fields;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

}

