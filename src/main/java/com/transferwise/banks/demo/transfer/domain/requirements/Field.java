package com.transferwise.banks.demo.transfer.domain.requirements;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Field {

    private String name;
    private List<Group> group = null;
    private Map<String, Object> additionalProperties = new HashMap<>();

    public Field() {
    }

    public Field(String name, List<Group> group, Map<String, Object> additionalProperties) {
        this.name = name;
        this.group = group;
        this.additionalProperties = additionalProperties;
    }

    public String getName() {
        return name;
    }

    public List<Group> getGroup() {
        return group;
    }

    public Map<String, Object> getAdditionalProperties() {
        return additionalProperties;
    }
}