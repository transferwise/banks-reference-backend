package com.transferwise.banks.demo.transfer.persistence;

import com.transferwise.banks.demo.recipient.domain.Recipient;
import com.transferwise.banks.demo.transfer.domain.CustomerTransfer;
import com.transferwise.banks.demo.transfer.domain.CustomerTransferPersistence;
import com.transferwise.banks.demo.transfer.domain.TransferWiseTransfer;
import com.transferwise.banks.demo.transfer.domain.status.CustomerTransferStatus;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

@Component
class CustomerTransferPersistenceImpl implements CustomerTransferPersistence {

    private final CustomerTransferRepository customerTransferRepository;

    CustomerTransferPersistenceImpl(CustomerTransferRepository customerTransferRepository) {
        this.customerTransferRepository = customerTransferRepository;
    }

    @Override
    public void saveCustomerTransfer(final Long customerId, final TransferWiseTransfer transferWiseTransfer, final Recipient recipient, final BigDecimal fee) {
        CustomerTransferEntity customerTransferEntity = mapToEntity(customerId, transferWiseTransfer, recipient, fee);
        customerTransferRepository.save(customerTransferEntity);
    }

    @Override
    public boolean existsById(final Long transferId) {
        return customerTransferRepository.existsById(transferId);
    }

    @Override
    public List<CustomerTransfer> getCustomerTransfers(Long customerId) {
        List<CustomerTransferEntity> customerTransferEntities = customerTransferRepository.findAllByCustomerId(customerId);

        return customerTransferEntities.stream()
                .map(this::mapToCustomerTransfer)
                .collect(toList());
    }

    private CustomerTransferEntity mapToEntity(final Long customerId, final TransferWiseTransfer transferWiseTransfer, final Recipient recipient, final BigDecimal fee) {
        return new CustomerTransferEntity(transferWiseTransfer.getId(),
                customerId,
                transferWiseTransfer.getTargetAccount(),
                transferWiseTransfer.getQuote().toString(),
                transferWiseTransfer.getReference(),
                transferWiseTransfer.getRate(),
                transferWiseTransfer.getCreated(),
                transferWiseTransfer.getSourceCurrency(),
                transferWiseTransfer.getSourceValue(),
                transferWiseTransfer.getTargetCurrency(),
                transferWiseTransfer.getTargetValue(),
                transferWiseTransfer.getCustomerTransactionId().toString(),
                recipient.getName().getFullName(),
                fee,
                recipient.getAccountSummary(),
                emptyList());

    }

    private CustomerTransfer mapToCustomerTransfer(final CustomerTransferEntity customerTransferEntity) {
        List<CustomerTransferStatus> customerTransferStatuses = customerTransferEntity.getTransferStatuses()
                .stream()
                .map(this::mapToCustomerTransferStatus)
                .collect(toList());

        return new CustomerTransfer(customerTransferEntity.getId(),
                customerTransferEntity.getCustomerId(),
                customerTransferEntity.getTargetAccount(),
                UUID.fromString(customerTransferEntity.getQuoteUuid()),
                customerTransferEntity.getReference(),
                customerTransferEntity.getRate(),
                customerTransferEntity.getCreated(),
                customerTransferEntity.getSourceCurrency(),
                customerTransferEntity.getSourceValue(),
                customerTransferEntity.getTargetCurrency(),
                customerTransferEntity.getTargetValue(),
                UUID.fromString(customerTransferEntity.getCustomerTransactionId()),
                customerTransferEntity.getRecipientName(),
                customerTransferEntity.getFee(),
                customerTransferEntity.getAccountSummary(),
                customerTransferStatuses);

    }

    private CustomerTransferStatus mapToCustomerTransferStatus(final CustomerTransferStatusEntity customerTransferStatusEntity) {
        return new CustomerTransferStatus(customerTransferStatusEntity.getCustomerTransferId(),
                customerTransferStatusEntity.getNewState(),
                customerTransferStatusEntity.getEventTime());
    }
}
