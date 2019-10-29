package com.transferwise.banks.demo.credentials.persistence.twusertokens;

import com.transferwise.banks.demo.credentials.domain.TWUserTokens;

import java.util.Optional;

public interface TWUserTokensPersistence {

    TWUserTokens save(TWUserTokens twUserTokens);

    Optional<TWUserTokens> findByCustomerId(Long customerId);
}
