package com.transferwise.banks.demo.customer.address.domain.twaddress;

import com.transferwise.banks.demo.credentials.domain.TWUserTokens;
import com.transferwise.banks.demo.credentials.domain.twprofile.TWProfile;
import com.transferwise.banks.demo.customer.address.domain.Address;
import com.transferwise.banks.demo.customer.address.twclient.TWAddressResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TWAddressService {

    Mono<TWAddressResponse> createAddress(TWUserTokens twUserTokens, TWProfile twProfile);
}
