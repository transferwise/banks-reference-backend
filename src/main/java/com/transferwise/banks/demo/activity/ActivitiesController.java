package com.transferwise.banks.demo.activity;

import com.transferwise.banks.demo.customer.Customer;
import com.transferwise.banks.demo.customer.CustomerTransfer;
import com.transferwise.banks.demo.customer.CustomersRepository;
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