package com.transferwise.banks.demo.customer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/customers")
public class CustomersController {

    private final CustomersRepository customers;

    public CustomersController(final CustomersRepository customers) {
        this.customers = customers;
    }

    @GetMapping
    public Customer show(@RequestParam final Long id) {
        return customers.find(id);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public Customer create(@Valid @RequestBody final NewCustomer newCustomer) {
        return customers.save(new Customer(newCustomer));
    }
}
