package com.transferwise.banks.demo.customer.address.occupation.persistence;

import com.transferwise.banks.demo.customer.address.occupation.domain.Occupation;
import com.transferwise.banks.demo.customer.address.occupation.domain.OccupationPersistence;
import com.transferwise.banks.demo.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Component;

@Component
class OccupationPersistenceSprintDataImpl implements OccupationPersistence {

    private final OccupationMapperPersistence occupationMapperPersistence;
    private final OccupationRepository occupationRepository;

    public OccupationPersistenceSprintDataImpl(OccupationMapperPersistence occupationMapperPersistence, OccupationRepository occupationRepository) {
        this.occupationMapperPersistence = occupationMapperPersistence;
        this.occupationRepository = occupationRepository;
    }

    @Override
    public Occupation findById(final Long addressId) {
        return
                occupationRepository.findById(addressId)
                .map(occupationMapperPersistence::mapToOccupation)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public Occupation save(final Occupation occupation) {
        OccupationEntity savedOccupationEntity = occupationRepository.save(occupationMapperPersistence.mapToOccupationEntity(occupation));
        return occupationMapperPersistence.mapToOccupation(savedOccupationEntity);
    }
}
