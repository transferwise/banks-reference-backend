package com.transferwise.banks.demo.transfer.status;

import com.transferwise.banks.demo.customer.CustomerTransferRepository;
import com.transferwise.banks.demo.customer.CustomerTransferStatus;
import com.transferwise.banks.demo.customer.CustomerTransferStatusRepository;
import com.transferwise.banks.demo.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TransferStatusService {

    private static final Logger log = LoggerFactory.getLogger(TransferStatusService.class);

    private final CustomerTransferStatusRepository customerTransferStatusRepository;
    private final CustomerTransferRepository customerTransferRepository;

    public TransferStatusService(CustomerTransferStatusRepository customerTransferStatusRepository, CustomerTransferRepository customerTransferRepository) {
        this.customerTransferStatusRepository = customerTransferStatusRepository;
        this.customerTransferRepository = customerTransferRepository;
    }

    public void transferStatusChange(final CustomerTransferStatus customerTransferStatus) {
        Long transferId = customerTransferStatus.getCustomerTransferId();

        boolean transferExists = customerTransferRepository.existsById(transferId);

        if (transferExists) {
            customerTransferStatusRepository.save(customerTransferStatus);
        } else {
            log.info("Couldn't find transfer with id {}", transferId);
            throw new ResourceNotFoundException();
        }
    }
}
