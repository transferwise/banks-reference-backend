package com.transferwise.banks.demo.recipient.twclient;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.transferwise.banks.demo.recipient.domain.Recipient;

import java.util.ArrayList;
import java.util.List;

class TWRecipientsContent {

    @JsonProperty("content")
    public final List<Recipient> recipients = new ArrayList<>();
}
