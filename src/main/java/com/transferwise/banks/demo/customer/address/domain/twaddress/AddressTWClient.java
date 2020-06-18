package com.transferwise.banks.demo.customer.address.domain.twaddress;

import com.transferwise.banks.demo.credentials.domain.TWUserTokens;
import com.transferwise.banks.demo.customer.address.twclient.TWAddressResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AddressTWClient {

    Mono<TWAddressResponse> getAddress(TWUserTokens twUserTokens, Long addressId);

    Mono<TWAddressResponse> createAddress(TWUserTokens twUserTokens, CreateAddress createAddress);
}
