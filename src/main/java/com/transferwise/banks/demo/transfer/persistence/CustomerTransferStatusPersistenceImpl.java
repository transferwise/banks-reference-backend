package com.transferwise.banks.demo.transfer.persistence;

import com.transferwise.banks.demo.transfer.domain.status.CustomerTransferStatus;
import com.transferwise.banks.demo.transfer.domain.status.CustomerTransferStatusPersistence;
import org.springframework.stereotype.Component;

@Component
class CustomerTransferStatusPersistenceImpl implements CustomerTransferStatusPersistence {

    private final CustomerTransferStatusRepository customerTransferStatusRepository;

    CustomerTransferStatusPersistenceImpl(CustomerTransferStatusRepository customerTransferStatusRepository) {
        this.customerTransferStatusRepository = customerTransferStatusRepository;
    }

    @Override
    public void save(CustomerTransferStatus customerTransferStatus) {
        customerTransferStatusRepository.save(mapToEntity(customerTransferStatus));
    }

    private CustomerTransferStatusEntity mapToEntity(CustomerTransferStatus customerTransferStatus) {
        return new CustomerTransferStatusEntity(customerTransferStatus.getCustomerTransferId(),
                customerTransferStatus.getNewState(),
                customerTransferStatus.getEventTime());
    }
}
