package com.transferwise.banks.demo.transfer.status;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.ZonedDateTime;

public class TransferStatusChangeData {

    private TransferStatusChangeResource resource;

    @JsonProperty("current_state")
    private String currentState;

    @JsonProperty("previous_state")
    private String previousState;

    @JsonProperty("occurred_at")
    private ZonedDateTime occurredAt;

    public TransferStatusChangeResource getResource() {
        return resource;
    }

    public String getCurrentState() {
        return currentState;
    }

    public String getPreviousState() {
        return previousState;
    }

    public ZonedDateTime getOccurredAt() {
        return occurredAt;
    }

    @Override
    public String toString() {
        return "TransferStatusChangeData{" +
                "resource=" + resource +
                ", currentState='" + currentState + '\'' +
                ", previousState='" + previousState + '\'' +
                ", occurredAt=" + occurredAt +
                '}';
    }
}
