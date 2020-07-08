package com.transferwise.banks.demo.customer.domain;

import com.transferwise.banks.demo.credentials.persistence.twprofile.TWProfilePersistence;
import org.springframework.stereotype.Component;

@Component
class CustomersServiceImpl implements CustomersService {

    private final CustomersPersistence customersPersistence;
    private final TWProfilePersistence twProfilePersistence;

    public CustomersServiceImpl(CustomersPersistence customersPersistence, TWProfilePersistence twProfilePersistence) {
        this.customersPersistence = customersPersistence;
        this.twProfilePersistence = twProfilePersistence;
    }

    public Customer find(Long id) {
        Customer customer = customersPersistence.findById(id);

        boolean transferWiseAccountLinked = twProfilePersistence.findByCustomerId(id)
                .isPresent();

        return customer.withTransferWiseAccountLinked(transferWiseAccountLinked);
    }

    public Customer save(Customer customer) {
        return customersPersistence.save(customer);
    }

}
