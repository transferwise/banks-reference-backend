package com.transferwise.banks.demo.transfer.domain.status;

import java.time.LocalDateTime;

public class CustomerTransferStatus {

    private Long customerTransferId;
    private String newState;
    private LocalDateTime eventTime;

    public CustomerTransferStatus(Long customerTransferId, String newState, LocalDateTime eventTime) {
        this.customerTransferId = customerTransferId;
        this.newState = newState;
        this.eventTime = eventTime;
    }

    public Long getCustomerTransferId() {
        return customerTransferId;
    }

    public String getNewState() {
        return newState;
    }

    public LocalDateTime getEventTime() {
        return eventTime;
    }
}
