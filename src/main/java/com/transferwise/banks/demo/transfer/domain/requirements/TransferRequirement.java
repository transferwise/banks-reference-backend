package com.transferwise.banks.demo.transfer.domain.requirements;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransferRequirement {

    private String type;
    private List<Field> fields = null;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public TransferRequirement(String type, List<Field> fields, Map<String, Object> additionalProperties) {
        this.type = type;
        this.fields = fields;
        this.additionalProperties = additionalProperties;
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

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}

