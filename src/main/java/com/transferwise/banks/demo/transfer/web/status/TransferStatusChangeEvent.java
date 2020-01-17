package com.transferwise.banks.demo.transfer.web.status;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.transferwise.banks.demo.transfer.domain.status.CustomerTransferStatus;

import java.time.ZonedDateTime;

class TransferStatusChangeEvent {

    private TransferStatusChangeData data;

    @JsonProperty("subscription_id")
    private String subscriptionId;

    @JsonProperty("event_type")
    private String eventType;

    @JsonProperty("schema_version")
    private String schemaVersion;

    @JsonProperty("sent_at")
    private ZonedDateTime sentAt;

    public TransferStatusChangeData getData() {
        return data;
    }

    public String getSubscriptionId() {
        return subscriptionId;
    }

    public String getEventType() {
        return eventType;
    }

    public String getSchemaVersion() {
        return schemaVersion;
    }

    public ZonedDateTime getSentAt() {
        return sentAt;
    }

    public CustomerTransferStatus toCustomerTransferStatus() {
        return new CustomerTransferStatus(this.getData().getResource().getId(),
                this.getData().getCurrentState(),
                this.getData().getOccurredAt().toLocalDateTime());
    }

    @Override
    public String toString() {
        return "TransferStatusChangeEvent{" +
                "data=" + data +
                ", subscriptionId='" + subscriptionId + '\'' +
                ", eventType='" + eventType + '\'' +
                ", schemaVersion='" + schemaVersion + '\'' +
                ", sentAt=" + sentAt +
                '}';
    }
}
