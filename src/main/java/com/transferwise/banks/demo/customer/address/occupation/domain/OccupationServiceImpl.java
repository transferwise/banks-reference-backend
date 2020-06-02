package com.transferwise.banks.demo.customer.address.occupation.domain;

import com.transferwise.banks.demo.customer.address.occupation.persistence.OccupationMapperPersistence;
import org.springframework.stereotype.Component;

class OccupationServiceImpl implements OccupationService {

    private final OccupationPersistence occupationPersistence;

    public OccupationServiceImpl(OccupationPersistence occupationPersistence) {
        this.occupationPersistence = occupationPersistence;
    }

    public Occupation find(Long id) {
        Occupation occupation = occupationPersistence.findById(id);

        return occupation;
    }

    public Occupation save(Occupation occupation) {
        return occupationPersistence.save(occupation);
    }
}
