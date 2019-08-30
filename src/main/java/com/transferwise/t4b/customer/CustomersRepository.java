package com.transferwise.t4b.customer;

import com.transferwise.t4b.exceptions.ResourceNotFoundException;
import org.springframework.data.repository.CrudRepository;

public interface CustomersRepository extends CrudRepository<Customer, Long> {

    default Customer find(final Long id) {
        return findById(id).orElseThrow(() -> new ResourceNotFoundException());
    }
}
