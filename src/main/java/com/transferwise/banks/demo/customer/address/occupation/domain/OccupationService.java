package com.transferwise.banks.demo.customer.address.occupation.domain;

public interface OccupationService {

    Occupation find(Long id);

    Occupation findByAddress(Long addressId);

    Occupation save(Occupation occupation);
}
