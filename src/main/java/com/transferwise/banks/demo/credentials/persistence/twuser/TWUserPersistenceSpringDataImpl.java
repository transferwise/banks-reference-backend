package com.transferwise.banks.demo.credentials.persistence.twuser;

import com.transferwise.banks.demo.credentials.domain.TWUser;
import org.springframework.stereotype.Component;

@Component
class TWUserPersistenceSpringDataImpl implements TWUserPersistence {

    private final TWUserRepository twUserRepository;

    TWUserPersistenceSpringDataImpl(TWUserRepository twUserRepository) {
        this.twUserRepository = twUserRepository;
    }

    @Override
    public TWUser save(TWUser twUser) {
        return mapToTWUser(twUserRepository.save(mapToEntity(twUser)));
    }

    private TWUserEntity mapToEntity(final TWUser twUser) {
        return new TWUserEntity(twUser.getTwUserId(),
                twUser.getRegistrationCode(),
                twUser.getCustomerId(),
                twUser.getEmail(),
                twUser.getActive());
    }

    private TWUser mapToTWUser(final TWUserEntity twUserEntity) {
        return new TWUser(twUserEntity.getTwUserId(),
                twUserEntity.getRegistrationCode(),
                twUserEntity.getCustomerId(),
                twUserEntity.getEmail(),
                twUserEntity.getActive());
    }
}
