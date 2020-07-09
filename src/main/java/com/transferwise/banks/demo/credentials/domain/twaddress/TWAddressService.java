package com.transferwise.banks.demo.credentials.domain.twaddress;

import com.transferwise.banks.demo.credentials.domain.TWUserTokens;
import com.transferwise.banks.demo.credentials.domain.twprofile.TWProfile;
import com.transferwise.banks.demo.credentials.twclient.twaddress.TWAddressResponse;
import com.transferwise.banks.demo.customer.domain.address.Address;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface TWAddressService {

    Mono<TWAddressResponse> createAddress(Address address, Optional<TWUserTokens> twUserTokens, Optional<TWProfile> twProfile);
}
