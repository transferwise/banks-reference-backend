package com.transferwise.banks.demo.credentials.persistence.twprofile;

import com.transferwise.banks.demo.credentials.domain.TWProfile;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
class TWProfilePersistenceSpringDataImpl implements TWProfilePersistence {

    private final TWProfileRepository twProfileRepository;

    TWProfilePersistenceSpringDataImpl(TWProfileRepository twProfileRepository) {
        this.twProfileRepository = twProfileRepository;
    }


    @Override
    public TWProfile save(TWProfile twProfile) {
        return mapToTWProfile(twProfileRepository.save(mapToEntity(twProfile)));
    }

    @Override
    public Optional<TWProfile> findByCustomerId(Long customerId) {
        return twProfileRepository.findByCustomerId(customerId)
                .map(this::mapToTWProfile);
    }

    private TWProfileEntity mapToEntity(final TWProfile twProfile) {
        return new TWProfileEntity(twProfile.getTwProfileId(),
                twProfile.getCustomerId(),
                twProfile.getType());
    }

    private TWProfile mapToTWProfile(final TWProfileEntity twProfileEntity) {
        return new TWProfile(twProfileEntity.getTwProfileId(),
                twProfileEntity.getCustomerId(),
                twProfileEntity.getType());
    }
}
