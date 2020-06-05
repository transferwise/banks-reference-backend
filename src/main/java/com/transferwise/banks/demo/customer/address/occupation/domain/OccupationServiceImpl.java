package com.transferwise.banks.demo.customer.address.occupation.domain;

import org.springframework.stereotype.Component;

@Component
class OccupationServiceImpl implements OccupationService {

    private final OccupationPersistence occupationPersistence;

    public OccupationServiceImpl(OccupationPersistence occupationPersistence) {
        this.occupationPersistence = occupationPersistence;
    }

    public Occupation find(Long id) {
        Occupation occupation = occupationPersistence.findById(id);

        return occupation;
    }

    public Occupation findByAddress(Long addressId) {
        Occupation occupation = occupationPersistence.findByAddressId(addressId);

        return occupation;
    }

    public Occupation save(Occupation occupation) {
        return occupationPersistence.save(occupation);
    }
}
