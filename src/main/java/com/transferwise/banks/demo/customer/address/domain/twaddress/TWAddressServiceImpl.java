package com.transferwise.banks.demo.customer.address.domain.twaddress;

import com.transferwise.banks.demo.credentials.domain.twprofile.TWProfile;
import com.transferwise.banks.demo.customer.address.domain.Address;
import com.transferwise.banks.demo.customer.address.domain.AddressPersistence;
import com.transferwise.banks.demo.credentials.domain.CredentialsManager;
import com.transferwise.banks.demo.credentials.persistence.twprofile.TWProfilePersistence;
import com.transferwise.banks.demo.customer.address.twclient.TWAddressResponse;
import com.transferwise.banks.demo.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
class TWAddressServiceImpl implements TWAddressService {

    private static final Logger log = LoggerFactory.getLogger(TWAddressServiceImpl.class);

    private final AddressTWClient addressTWClient;
    private final CredentialsManager credentialsManager;
    private final TWProfilePersistence twProfilePersistence;
    private final AddressPersistence addressPersistence;

    TWAddressServiceImpl(TWProfilePersistence twProfilePersistence, AddressTWClient addressTWClient, CredentialsManager credentialsManager, AddressPersistence addressPersistence) {
        this.addressTWClient = addressTWClient;
        this.credentialsManager = credentialsManager;
        this.twProfilePersistence = twProfilePersistence;
        this.addressPersistence = addressPersistence;
    }

    @Override
    public Mono<TWAddressResponse> getAddress(Long addressId, Long customerId) {

        return twProfilePersistence.findByCustomerId(customerId)
                .map(twProfile -> credentialsManager.refreshTokens(customerId)
                        .flatMap(twUserTokens ->
                                addressTWClient.getAddress(twUserTokens, addressId)))
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public Mono<TWAddressResponse> createAddress(Long customerId) {
        final TWProfile twProfileOptional = twProfilePersistence.findByCustomerId(customerId).filter(profile -> profile.getCustomerId() == customerId).get();
        final CreateAddress createAddress = new CreateAddress(twProfileOptional.getTwProfileId(),
                mapToTwAddress(addressPersistence.findByCustomerId(customerId)));

        return twProfilePersistence.findByCustomerId(customerId)
                .map(twProfile -> credentialsManager.refreshTokens(customerId)
                        .flatMap(twUserTokens ->
                                addressTWClient.createAddress(twUserTokens, createAddress)))
                .orElseThrow(ResourceNotFoundException::new);
    }

    private TWAddress mapToTwAddress(Address address){
        return new TWAddress(address);
    }
}
