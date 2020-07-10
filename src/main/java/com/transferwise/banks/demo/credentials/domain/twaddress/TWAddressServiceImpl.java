package com.transferwise.banks.demo.credentials.domain.twaddress;

import com.transferwise.banks.demo.credentials.domain.TWUserTokens;
import com.transferwise.banks.demo.credentials.domain.twprofile.TWProfile;
import com.transferwise.banks.demo.customer.domain.address.Address;
import com.transferwise.banks.demo.credentials.twclient.twaddress.TWAddressResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
class TWAddressServiceImpl implements TWAddressService {

    private final AddressTWClient addressTWClient;

    TWAddressServiceImpl(AddressTWClient addressTWClient) {
        this.addressTWClient = addressTWClient;
    }

    @Override
    public Mono<TWAddressResponse> createAddress(Address address, Optional<TWUserTokens> twUserTokens, Optional<TWProfile> twProfile) {

            final TWAddress twAddress = new TWAddress(address);
            final CreateAddress createAddress = new CreateAddress(twProfile.get().getTwProfileId(), twAddress);

            return addressTWClient.createAddress(twUserTokens, createAddress);
    }
}
