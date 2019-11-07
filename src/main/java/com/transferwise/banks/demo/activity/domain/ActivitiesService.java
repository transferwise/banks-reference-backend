package com.transferwise.banks.demo.activity.domain;

import com.transferwise.banks.demo.transfer.domain.CustomerTransfer;

import java.util.List;

public interface ActivitiesService {

    List<CustomerTransfer> getCustomerTransfers(Long customerId);
}
