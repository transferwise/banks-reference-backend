package com.transferwise.banks.demo.utilities.web;

import com.transferwise.banks.demo.customer.domain.Customer;
import com.transferwise.banks.demo.customer.domain.CustomersService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Random;

/**
 * Offers endpoints specifically for demo purposes on the Mobile Reference App.
 *
 * ⚠️ This is only part of the repository for demo purposes, but has no value as reference code. ⚠️
 */

@RestController
@RequestMapping("demo")
public class DemoUtilitiesController {

    private final CustomersService customersService;

    public DemoUtilitiesController(CustomersService customersService) {
        this.customersService = customersService;
    }

    @PostMapping("new-customer")
    public String createNewDemoCustomer() {
        Customer newDemoCustomer = new Customer("Test", "Name", randomizeDOB(), randomizeTestEmail(), randomizePhone());

        newDemoCustomer = customersService.save(newDemoCustomer);
        return newDemoCustomer.getId().toString();
    }

    private String randomizePhone() {
        var phoneNo = 0;
        var randomizer = new Random();
        for (int i = 8; i > 0; i--){
            phoneNo += Math.abs(randomizer.nextInt(9)) * Math.pow(10,i);
        }
        return "07".concat(String.valueOf(phoneNo));
    }

    private LocalDate randomizeDOB() {
        var rDay = 1+Math.abs((new Random()).nextInt(28));
        var rMonth = 1+Math.abs((new Random()).nextInt(11));
        var rYear = 1900+Math.abs((new Random()).nextInt(100));

        return LocalDate.of(rYear, rMonth, rDay);
    }

    private String randomizeTestEmail() {
        var aPositiveNumber = String.valueOf(Math.abs((new Random()).nextInt(9999)));
        return "test.reference.email+".concat(aPositiveNumber).concat("@gmail.com");
    }

}
