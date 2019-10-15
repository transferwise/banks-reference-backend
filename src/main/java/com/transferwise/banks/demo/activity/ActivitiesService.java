package com.transferwise.banks.demo.activity;

import com.transferwise.banks.demo.customer.CustomerTransfer;
import com.transferwise.banks.demo.customer.CustomersRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

@Service
public class ActivitiesService {

    private final CustomersRepository customersRepository;

    public ActivitiesService(final CustomersRepository customersRepository) {
        this.customersRepository = customersRepository;
    }

    public List<CustomerTransfer> getCustomerTransfers(final Long customerId) {
        final var customer = customersRepository.find(customerId);

        return customer.getCustomerTransfers()
                .stream()
                .sorted(comparing(CustomerTransfer::getCreated))
                .collect(toList());
    }
}
