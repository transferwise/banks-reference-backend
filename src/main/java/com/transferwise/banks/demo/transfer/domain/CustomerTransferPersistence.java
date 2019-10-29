package com.transferwise.banks.demo.transfer.domain;

import com.transferwise.banks.demo.quote.Quote;
import com.transferwise.banks.demo.recipient.domain.Recipient;

import java.util.List;

public interface CustomerTransferPersistence {

    void saveCustomerTransfer(Long customerId, TransferWiseTransfer transferWiseTransfer, Recipient recipient, Quote quote);

    boolean existsById(Long transferId);

    List<CustomerTransfer> getCustomerTransfers(Long customerId);

}
