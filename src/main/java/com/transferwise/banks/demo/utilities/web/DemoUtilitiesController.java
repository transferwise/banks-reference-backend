package com.transferwise.banks.demo.utilities.web;

import com.transferwise.banks.demo.customer.domain.Customer;
import com.transferwise.banks.demo.customer.domain.CustomersService;
import com.transferwise.banks.demo.customer.domain.address.Address;
import com.transferwise.banks.demo.customer.domain.occupation.Occupation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

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
        List<Occupation> occupations = new ArrayList<>();
        occupations.add(new Occupation("Software Engineer", "FREE_FORM"));
        Address address = new Address("56, Shoreditch High Street", "E1 6JJ", "London", "", "GB", occupations);

        Customer newDemoCustomer = new Customer("Test", "Name",
                demoUtilitiesService.randomizeDOB(), demoUtilitiesService.randomizePhone(), demoUtilitiesService.randomizeEmail(), address);

        newDemoCustomer = customersService.save(newDemoCustomer);
        return newDemoCustomer.getId();
    }

}
