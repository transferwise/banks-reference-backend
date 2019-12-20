package com.transferwise.banks.demo.transfer.domain.requirements;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Field {

    private String name;
    private List<Group> group = null;

    public Field() {
    }

    public Field(String name, List<Group> group) {
        this.name = name;
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public List<Group> getGroup() {
        return group;
    }

}