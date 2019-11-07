package com.transferwise.banks.demo.activity.domain;

import com.transferwise.banks.demo.transfer.domain.CustomerTransfer;
import com.transferwise.banks.demo.transfer.domain.CustomerTransferPersistence;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

@Component
class ActivitiesServiceImpl implements ActivitiesService {

    private final CustomerTransferPersistence customerTransferPersistence;

    ActivitiesServiceImpl(CustomerTransferPersistence customerTransferPersistence) {
        this.customerTransferPersistence = customerTransferPersistence;
    }

    public List<CustomerTransfer> getCustomerTransfers(final Long customerId) {
        return customerTransferPersistence.getCustomerTransfers(customerId)
                .stream()
                .sorted(comparing(CustomerTransfer::getCreated))
                .collect(toList());
    }
}
