package com.transferwise.banks.demo.transfer.domain.status;

import com.transferwise.banks.demo.exceptions.ResourceNotFoundException;
import com.transferwise.banks.demo.transfer.domain.CustomerTransferPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
class TransferStatusServiceImpl implements TransferStatusService {

    private static final Logger log = LoggerFactory.getLogger(TransferStatusServiceImpl.class);

    private final CustomerTransferPersistence customerTransferPersistence;
    private final CustomerTransferStatusPersistence customerTransferStatusPersistence;

    public TransferStatusServiceImpl(CustomerTransferPersistence customerTransferPersistence, CustomerTransferStatusPersistence customerTransferStatusPersistence) {
        this.customerTransferPersistence = customerTransferPersistence;
        this.customerTransferStatusPersistence = customerTransferStatusPersistence;
    }

    public void transferStatusChange(final CustomerTransferStatus customerTransferStatus) {
        Long transferId = customerTransferStatus.getCustomerTransferId();

        boolean transferExists = customerTransferPersistence.existsById(transferId);

        if (transferExists) {
            customerTransferStatusPersistence.save(customerTransferStatus);
        } else {
            log.info("Couldn't find transfer with id {}", transferId);
            throw new ResourceNotFoundException();
        }
    }
}
