package com.transferwise.banks.demo.activity;

import com.transferwise.banks.demo.customer.Customer;
import com.transferwise.banks.demo.customer.CustomerTransfer;
import com.transferwise.banks.demo.customer.CustomersRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Comparator.comparing;

@Service
public class ActivitiesService {

    private final CustomersRepository customersRepository;

    public ActivitiesService(CustomersRepository customersRepository) {
        this.customersRepository = customersRepository;
    }

    public List<CustomerTransfer> getCustomerTransfers(final Long customerId) {
        Customer customer = customersRepository.find(customerId);

        List<CustomerTransfer> customerTransfers = customer.getCustomerTransfers();
        customerTransfers.sort(comparing(CustomerTransfer::getCreated));

        return customerTransfers;
    }
}
