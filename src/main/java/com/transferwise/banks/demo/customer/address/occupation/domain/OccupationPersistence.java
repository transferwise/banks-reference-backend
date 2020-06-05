package com.transferwise.banks.demo.customer.address.occupation.domain;

public interface OccupationPersistence {

    Occupation findById(Long id);

    Occupation findByAddressId(Long addressId);

    Occupation save(Occupation occupation);
}
