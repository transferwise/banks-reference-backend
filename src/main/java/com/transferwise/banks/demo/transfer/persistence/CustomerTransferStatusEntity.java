package com.transferwise.banks.demo.transfer.persistence;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "customer_transfers_status")
class CustomerTransferStatusEntity {

    @Id
    @GeneratedValue
    private Long id;

    private Long customerTransferId;
    private String newState;
    private LocalDateTime eventTime;

    public CustomerTransferStatusEntity() {
    }

    public CustomerTransferStatusEntity(Long customerTransferId, String newState, LocalDateTime eventTime) {
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
