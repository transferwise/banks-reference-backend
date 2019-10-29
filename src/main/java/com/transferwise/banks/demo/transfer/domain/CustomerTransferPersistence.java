package com.transferwise.banks.demo.transfer.domain;

import com.transferwise.banks.demo.recipient.domain.Recipient;

import java.math.BigDecimal;
import java.util.List;

public interface CustomerTransferPersistence {

    void saveCustomerTransfer(Long customerId, TransferWiseTransfer transferWiseTransfer, Recipient recipient, BigDecimal fee);

    boolean existsById(Long transferId);

    List<CustomerTransfer> getCustomerTransfers(Long customerId);

}
