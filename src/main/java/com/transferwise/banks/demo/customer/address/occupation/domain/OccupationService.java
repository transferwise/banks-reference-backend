package com.transferwise.banks.demo.customer.address.occupation.domain;

import java.util.List;

public interface OccupationService {

    Occupation find(Long id);

    List<Occupation> findByAddress(Long addressId);

    Occupation save(Occupation occupation);
}
