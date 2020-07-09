package com.transferwise.banks.demo.credentials.domain.twaddress;

import com.transferwise.banks.demo.credentials.domain.TWUserTokens;
import com.transferwise.banks.demo.credentials.twclient.twaddress.TWAddressResponse;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface AddressTWClient {

    Mono<TWAddressResponse> createAddress(Optional<TWUserTokens> twUserTokens, CreateAddress createAddress);
}
