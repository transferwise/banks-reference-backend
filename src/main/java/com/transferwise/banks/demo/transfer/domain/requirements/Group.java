package com.transferwise.banks.demo.transfer.domain.requirements;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Group {
    private String key;
    private String name;
    private String type;
    private Boolean refreshRequirementsOnChange;
    private Boolean required;
    private String displayFormat;
    private String example;
    private Integer minLength;
    private Integer maxLength;
    private String validationRegexp;
    private String validationAsync;
    private List<ValuesAllowed> valuesAllowed = null;
    private Map<String, Object> additionalProperties = new HashMap<>();

    public Group() {
    }

    public Group(String key, String name, String type, Boolean refreshRequirementsOnChange, Boolean required, String displayFormat, String example, Integer minLength, Integer maxLength, String validationRegexp, String validationAsync, List<ValuesAllowed> valuesAllowed, Map<String, Object> additionalProperties) {
        this.key = key;
        this.name = name;
        this.type = type;
        this.refreshRequirementsOnChange = refreshRequirementsOnChange;
        this.required = required;
        this.displayFormat = displayFormat;
        this.example = example;
        this.minLength = minLength;
        this.maxLength = maxLength;
        this.validationRegexp = validationRegexp;
        this.validationAsync = validationAsync;
        this.valuesAllowed = valuesAllowed;
        this.additionalProperties = additionalProperties;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Boolean getRefreshRequirementsOnChange() {
        return refreshRequirementsOnChange;
    }

    public Boolean getRequired() {
        return required;
    }

    public String getDisplayFormat() {
        return displayFormat;
    }

    public String getExample() {
        return example;
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

    public String getValidationAsync() {
        return validationAsync;
    }

    public List<ValuesAllowed> getValuesAllowed() {
        return valuesAllowed;
    }

    public Map<String, Object> getAdditionalProperties() {
        return additionalProperties;
    }
}