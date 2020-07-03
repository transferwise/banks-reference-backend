package com.transferwise.banks.demo.customer.persistence;

import com.transferwise.banks.demo.customer.domain.address.Address;
import com.transferwise.banks.demo.customer.domain.address.AddressPersistence;
import com.transferwise.banks.demo.customer.domain.Customer;
import com.transferwise.banks.demo.customer.domain.occupation.Occupation;
import com.transferwise.banks.demo.customer.persistence.address.AddressEntity;
import com.transferwise.banks.demo.customer.persistence.occupation.OccupationEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
class CustomersMapperPersistence {

    public Customer mapToCustomer(final CustomerEntity customerEntity) {
        return new Customer(customerEntity.getId(),
                customerEntity.getFirstName(),
                customerEntity.getLastName(),
                customerEntity.getDateOfBirth(),
                customerEntity.getPhoneNumber(),
                customerEntity.getEmail(),
                mapToAddress(customerEntity.getAddress()));
    }

    public Address mapToAddress(final AddressEntity addressEntity) {
        return new Address(addressEntity.getFirstLine(),
                addressEntity.getPostCode(),
                addressEntity.getCity(),
                addressEntity.getState(),
                addressEntity.getCountry(),
                mapToOccupation(addressEntity.getOccupations()));
    }

    public List<Occupation> mapToOccupation(final List<OccupationEntity> occupationEntities) {
        List<Occupation> occupationList = new ArrayList<>();

        for(OccupationEntity occupationEntity : occupationEntities) {
            occupationList.add(new Occupation(
                    occupationEntity.getCode(),
                    occupationEntity.getFormat()
            ));
        }

        return occupationList;
    }

    public CustomerEntity mapToCustomerEntity(final Customer customer) {
        return new CustomerEntity(customer.getFirstName(),
                customer.getLastName(),
                customer.getDateOfBirth(),
                customer.getPhoneNumber(),
                customer.getEmail(),
                mapToAddressEntity(customer.getAddress()));
    }

    public AddressEntity mapToAddressEntity(final Address address) {
        return new AddressEntity(address.getFirstLine(),
                address.getPostCode(),
                address.getCity(),
                address.getState(),
                address.getCountry(),
                mapToOccupationEntity(address.getOccupations()));
    }

    public List<OccupationEntity> mapToOccupationEntity(final List<Occupation> occupations) {
        List<OccupationEntity> occupationList = new ArrayList<>();

        for(Occupation occupation : occupations) {
            occupationList.add(new OccupationEntity(
                    occupation.getCode(),
                    occupation.getFormat()
            ));
        }

        return occupationList;
    }
}
