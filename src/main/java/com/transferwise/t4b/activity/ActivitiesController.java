package com.transferwise.t4b.activity;

import com.transferwise.t4b.customer.Customer;
import com.transferwise.t4b.customer.CustomerTransfer;
import com.transferwise.t4b.customer.CustomersRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("activities")
public class ActivitiesController {

    private final CustomersRepository customersRepository;

    public ActivitiesController(CustomersRepository customersRepository) {
        this.customersRepository = customersRepository;
    }

    @GetMapping
    public Set<CustomerTransfer> getActivities(@RequestParam final Long customerId) {
        Customer customer = customersRepository.find(customerId);
        return customer.getCustomerTransfers();
    }

}