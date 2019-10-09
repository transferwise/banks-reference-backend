package com.transferwise.banks.demo.recipient;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Content {

    @JsonProperty("content")
    public final List<Recipient> recipients = new ArrayList<>();
}
