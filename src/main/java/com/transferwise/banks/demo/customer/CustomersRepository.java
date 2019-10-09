package com.transferwise.banks.demo.customer;

import com.transferwise.banks.demo.exceptions.ResourceNotFoundException;
import org.springframework.data.repository.CrudRepository;

public interface CustomersRepository extends CrudRepository<Customer, Long> {

    default Customer find(final Long id) {
        return findById(id).orElseThrow(() -> new ResourceNotFoundException());
    }
}
