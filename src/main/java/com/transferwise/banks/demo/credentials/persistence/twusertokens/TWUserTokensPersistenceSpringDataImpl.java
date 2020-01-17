package com.transferwise.banks.demo.credentials.persistence.twusertokens;

import com.transferwise.banks.demo.credentials.domain.TWUserTokens;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
class TWUserTokensPersistenceSpringDataImpl implements TWUserTokensPersistence {

    private final TWUserTokensRepository twUserTokensRepository;

    TWUserTokensPersistenceSpringDataImpl(TWUserTokensRepository twUserTokensRepository) {
        this.twUserTokensRepository = twUserTokensRepository;
    }

    @Override
    public TWUserTokens save(TWUserTokens twUserTokens) {
        return mapToTWUserTokens(twUserTokensRepository.save(mapToEntity(twUserTokens)));
    }

    @Override
    public Optional<TWUserTokens> findByCustomerId(Long customerId) {

        return twUserTokensRepository.findByCustomerId(customerId)
                .map(this::mapToTWUserTokens);
    }

    private TWUserTokensEntity mapToEntity(final TWUserTokens twUserTokens) {
        return new TWUserTokensEntity(twUserTokens.getCustomerId(),
                twUserTokens.getTwUserId(),
                twUserTokens.getAccessToken(),
                twUserTokens.getRefreshToken(),
                twUserTokens.getExpiryTime());
    }

    private TWUserTokens mapToTWUserTokens(final TWUserTokensEntity twUserTokensEntity) {
        return new TWUserTokens(twUserTokensEntity.getCustomerId(),
                twUserTokensEntity.getTwUserId(),
                twUserTokensEntity.getAccessToken(),
                twUserTokensEntity.getRefreshToken(),
                twUserTokensEntity.getExpiryTime());
    }
}
