package com.transferwise.t4b.recipient;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Content {

    @JsonProperty("content")
    public final List<Recipient> recipients = new ArrayList<>();
}
