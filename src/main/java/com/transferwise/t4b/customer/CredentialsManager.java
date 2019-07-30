package com.transferwise.t4b.customer;

import com.transferwise.t4b.client.ApiClient;
import com.transferwise.t4b.client.Credentials;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
public class CredentialsManager {

    private final ApiClient client;
    private final CustomerRepository customers;

    public CredentialsManager(final ApiClient client, final CustomerRepository customers) {
        this.client = client;
        this.customers = customers;
    }

    public Mono<Credentials> getCredentials(final Long customerId, final String code) {
        final Optional<Customer> customer = customers.findById(customerId);
        return customer
                .filter(Customer::hasCredentials)
                .map(this::refreshCredentials)
                .orElseGet(() -> createCredentials(customer.get(), code));
    }

    private Mono<Credentials> refreshCredentials(final Customer customer) {
        return client
                .refresh(customer.credentials)
                .doOnSuccess(creds -> save(customer, creds));
    }

    private Mono<Credentials> createCredentials(final Customer customer, final String code) {
        return client
                .accessCredentials(code)
                .doOnSuccess(creds -> save(customer, creds));
    }

    private Credentials save(final Customer customer, final Credentials credentials) {
        customer.credentials = credentials;
        customers.save(customer);
        return customer.credentials;
    }
}
