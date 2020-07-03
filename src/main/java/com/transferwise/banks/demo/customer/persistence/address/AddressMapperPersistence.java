package com.transferwise.banks.demo.customer.persistence.address;

import com.transferwise.banks.demo.customer.domain.address.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressMapperPersistence {

    public Address mapToCustomerAddress(final AddressEntity addressEntity) {
        return new Address(
                addressEntity.getFirstLine(),
                addressEntity.getPostCode(),
                addressEntity.getCity(),
                addressEntity.getState(),
                addressEntity.getCountry()
        );
    }

    public AddressEntity mapToCustomerAddressEntity(final Address address) {
        return new AddressEntity(
                address.getFirstLine(),
                address.getPostCode(),
                address.getCity(),
                address.getState(),
                address.getCountry()
        );
    }
}
