package com.transferwise.banks.demo.credentials.domain.twprofile;

import com.transferwise.banks.demo.credentials.domain.TWUserTokens;
import com.transferwise.banks.demo.customer.domain.Customer;
import reactor.core.publisher.Mono;

public interface ProfileService {

    Mono<TWProfile> getPersonalProfile(TWUserTokens twUserTokens);

    Mono<TWProfile> createPersonalProfile(TWUserTokens twUserTokens, Customer customer);

    void performRefreshCycle(TWUserTokens twUserTokens, Long customerId);
}
