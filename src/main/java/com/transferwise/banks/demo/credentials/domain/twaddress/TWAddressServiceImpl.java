package com.transferwise.banks.demo.credentials.domain.twaddress;

import com.transferwise.banks.demo.credentials.domain.TWUserTokens;
import com.transferwise.banks.demo.credentials.domain.twprofile.TWProfile;
import com.transferwise.banks.demo.customer.domain.address.Address;
import com.transferwise.banks.demo.customer.domain.address.AddressPersistence;
import com.transferwise.banks.demo.credentials.twclient.twaddress.TWAddressResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
class TWAddressServiceImpl implements TWAddressService {

    private static final Logger log = LoggerFactory.getLogger(TWAddressServiceImpl.class);

    private final AddressTWClient addressTWClient;
    private final AddressPersistence addressPersistence;

    TWAddressServiceImpl(AddressTWClient addressTWClient, AddressPersistence addressPersistence) {
        this.addressTWClient = addressTWClient;
        this.addressPersistence = addressPersistence;
    }

    @Override
    public Mono<TWAddressResponse> createAddress(Optional<TWUserTokens> twUserTokens, Optional<TWProfile> twProfile) {
        final Address address = addressPersistence.findByCustomerId(twProfile.get().getCustomerId());

        if(address.getId() != null){
            final TWAddress twAddress = new TWAddress(address);
            final CreateAddress createAddress = new CreateAddress(twProfile.get().getTwProfileId(), twAddress);

            return addressTWClient.createAddress(twUserTokens, createAddress);
        }

        return null;
    }
}
