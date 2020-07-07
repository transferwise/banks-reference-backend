package com.transferwise.banks.demo.customer.persistence.occupation;

import com.transferwise.banks.demo.customer.domain.occupation.Occupation;
import com.transferwise.banks.demo.customer.domain.occupation.OccupationPersistence;
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
    public Occupation save(final Occupation occupation) {
        OccupationEntity savedOccupationEntity = occupationRepository.save(occupationMapperPersistence.mapToOccupationEntity(occupation));
        return occupationMapperPersistence.mapToOccupation(savedOccupationEntity);
    }
}
