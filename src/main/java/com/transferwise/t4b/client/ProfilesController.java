package com.transferwise.t4b.client;

import com.transferwise.t4b.customer.CustomerRepository;
import com.transferwise.t4b.customer.Profile;
import org.reactivestreams.Publisher;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping
    public Publisher<Profile> index() {
        return customers
                .findById(1L)
                .map(c -> c.credentials)
                .map(this::profiles)
                .get();
    }

    private Publisher<Profile> profiles(final Credentials credentials) {
        return client.profiles(credentials.accessToken);
    }
}
