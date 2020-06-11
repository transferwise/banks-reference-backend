package com.transferwise.banks.demo.customer.address.persistence;

import com.transferwise.banks.demo.customer.address.domain.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressMapperPersistence {

    public Address mapToCustomerAddress(final AddressEntity addressEntity) {
        return new Address(
                addressEntity.getId(),
                addressEntity.getFirstLine(),
                addressEntity.getPostCode(),
                addressEntity.getCity(),
                addressEntity.getState(),
                addressEntity.getCountry(),
                addressEntity.getCustomerId()
        );
    }

    public AddressEntity mapToCustomerAddressEntity(final Address address) {
        return new AddressEntity(
                address.getFirstLine(),
                address.getPostCode(),
                address.getCity(),
                address.getState(),
                address.getCountry(),
                address.getCustomerId()
        );
    }
}
