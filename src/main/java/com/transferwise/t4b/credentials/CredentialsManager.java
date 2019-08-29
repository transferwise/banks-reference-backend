package com.transferwise.t4b.credentials;

import com.transferwise.t4b.client.ApiClient;
import com.transferwise.t4b.client.params.Code;
import com.transferwise.t4b.customer.Customer;
import com.transferwise.t4b.customer.CustomerRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CredentialsManager {

    private final ApiClient client;
    private final CustomerRepository customers;

    public CredentialsManager(final ApiClient client, final CustomerRepository customers) {
        this.client = client;
        this.customers = customers;
    }

    public Mono<Customer> generate(final Long customerId, final Code code) {
        final var customer = customers.find(customerId);
        return client
                .attachCredentialsAndProfiles(code, customer)
                .map(customers::save);
    }

    public Mono<Credentials> getCredentials(final Long customerId) {
        final var customer = customers.findById(customerId);
        return customer
                .filter(Customer::hasExpiredCredentials)
                .map(this::refresh)
                .orElseGet(() -> Mono.just(customer.get().getCredentials()));
    }

    private Mono<Credentials> refresh(final Customer customer) {
        return client
                .refresh(customer.getCredentials())
                .doOnSuccess(creds -> save(customer, creds));
    }

    private Credentials save(final Customer customer, final Credentials credentials) {
        return customers
                .save(customer.withCredentials(credentials))
                .getCredentials();
    }
}
