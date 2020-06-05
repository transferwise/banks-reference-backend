package com.transferwise.banks.demo.customer.address.occupation.domain;

import java.util.List;

public interface OccupationPersistence {

    Occupation findById(Long id);

    List<Occupation> findByAddressId(Long addressId);

    Occupation save(Occupation occupation);
}
