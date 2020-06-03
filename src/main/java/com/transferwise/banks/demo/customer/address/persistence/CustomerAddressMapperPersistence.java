package com.transferwise.banks.demo.customer.address.persistence;

import com.transferwise.banks.demo.customer.address.domain.CustomerAddress;
import org.springframework.stereotype.Component;

@Component
public class CustomerAddressMapperPersistence {

    public CustomerAddress mapToCustomerAddress(final CustomerAddressEntity customerAddressEntity) {
        return new CustomerAddress(
                customerAddressEntity.getId(),
                customerAddressEntity.getFirstLine(),
                customerAddressEntity.getPostCode(),
                customerAddressEntity.getCity(),
                customerAddressEntity.getState(),
                customerAddressEntity.getCountry(),
                customerAddressEntity.getCustomerId()
        );
    }

    public CustomerAddressEntity mapToCustomerAddressEntity(final CustomerAddress customerAddress) {
        return new CustomerAddressEntity(
                customerAddress.getFirstLine(),
                customerAddress.getPostCode(),
                customerAddress.getCity(),
                customerAddress.getState(),
                customerAddress.getCountry(),
                customerAddress.getCustomerId()
        );
    }
}
