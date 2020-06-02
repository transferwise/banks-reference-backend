package com.transferwise.banks.demo.customer.address.occupation.domain;

public interface OccupationService {

    Occupation find(Long id);

    Occupation save(Occupation occupation);
}
