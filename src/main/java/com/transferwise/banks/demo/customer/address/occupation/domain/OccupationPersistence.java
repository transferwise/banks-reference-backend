package com.transferwise.banks.demo.customer.address.occupation.domain;

public interface OccupationPersistence {

    Occupation findById(Long addressId);

    Occupation save(Occupation occupation);
}
