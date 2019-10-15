package com.transferwise.banks.demo.customer;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "customer_transfers_status")
public class CustomerTransferStatus {

    @Id
    @GeneratedValue
    private Long id;

    private Long customerTransferId;
    private String newState;
    private LocalDateTime eventTime;

    public CustomerTransferStatus() {
    }

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
