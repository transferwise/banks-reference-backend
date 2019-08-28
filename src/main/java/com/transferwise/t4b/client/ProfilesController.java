package com.transferwise.t4b.client;

import com.transferwise.t4b.customer.CustomerRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/profiles")
public class ProfilesController {

    private final ApiClient client;
    private final CustomerRepository customers;

    public ProfilesController(final ApiClient client, final CustomerRepository customers) {
        this.client = client;
        this.customers = customers;
    }

//    @GetMapping
//    public Publisher<Profile> index() {
//        return customers
//                .findById(1L)
//                .map(this::profiles)
//                .get();
//    }
//
//    private Publisher<Profile> profiles(final Customer customer) {
//        return client.profiles(customer.accessToken());
//    }
}
