package com.transferwise.t4b.activity;

import com.transferwise.t4b.customer.Customer;
import com.transferwise.t4b.customer.CustomerTransfer;
import com.transferwise.t4b.customer.CustomersRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ActivityService {

    private final CustomersRepository customersRepository;

    public ActivityService(CustomersRepository customersRepository) {
        this.customersRepository = customersRepository;
    }

    public Set<CustomerTransfer> getActivities(Long customerId) {
        Customer customer = customersRepository.find(customerId);
        return customer.getCustomerTransfers();
    }

}
