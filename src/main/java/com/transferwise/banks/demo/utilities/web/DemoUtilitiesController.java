package com.transferwise.banks.demo.utilities.web;

import com.transferwise.banks.demo.customer.domain.Customer;
import com.transferwise.banks.demo.customer.domain.CustomersService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Offers endpoints specifically for demo purposes on the Mobile Reference App.
 *
 * ⚠️ This is only part of the repository for demo purposes, but has no value as reference code. ⚠️
 */

@RestController
@RequestMapping("demo")
public class DemoUtilitiesController {

    private final CustomersService customersService;
    private final DemoUtilities demoUtilitiesService;

    public DemoUtilitiesController(CustomersService customersService) {
        this.customersService = customersService;
        this.demoUtilitiesService = new DemoUtilities();
    }

    @PostMapping("new-customer")
    public Long createNewDemoCustomer() {
        Customer newDemoCustomer = new Customer("Test", "Name",
                demoUtilitiesService.randomizeDOB(), demoUtilitiesService.randomizeEmail(), demoUtilitiesService.randomizePhone());

        newDemoCustomer = customersService.save(newDemoCustomer);
        return newDemoCustomer.getId();
    }

}
