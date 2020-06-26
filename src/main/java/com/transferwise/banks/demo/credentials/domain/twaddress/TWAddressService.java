package com.transferwise.banks.demo.credentials.domain.twaddress;

import com.transferwise.banks.demo.credentials.domain.TWUserTokens;
import com.transferwise.banks.demo.credentials.domain.twprofile.TWProfile;
import com.transferwise.banks.demo.credentials.twclient.twaddress.TWAddressResponse;
import reactor.core.publisher.Mono;

public interface TWAddressService {

    Mono<TWAddressResponse> createAddress(TWUserTokens twUserTokens, TWProfile twProfile);
}
