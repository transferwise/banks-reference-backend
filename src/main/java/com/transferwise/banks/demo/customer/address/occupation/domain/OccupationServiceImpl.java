package com.transferwise.banks.demo.customer.address.occupation.domain;

import org.springframework.stereotype.Component;

import java.util.List;

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

    public List<Occupation> findByAddress(Long addressId) {
        List<Occupation> occupations = occupationPersistence.findByAddressId(addressId);

        return occupations;
    }

    public Occupation save(Occupation occupation) {
        return occupationPersistence.save(occupation);
    }
}
