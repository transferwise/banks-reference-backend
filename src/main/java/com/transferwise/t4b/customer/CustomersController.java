package com.transferwise.t4b.customer;

import com.transferwise.t4b.exceptions.ResourceNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers")
public class CustomersController {

    private final CustomerRepository customers;

    public CustomersController(final CustomerRepository customers) {
        this.customers = customers;
    }

    @GetMapping
    public Customer show(@RequestParam final Long id) {
        return customers.findById(id).orElseThrow(() -> new ResourceNotFoundException());
    }
}
