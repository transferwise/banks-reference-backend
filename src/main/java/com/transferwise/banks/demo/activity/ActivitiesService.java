package com.transferwise.banks.demo.activity;

import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

@Component
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
