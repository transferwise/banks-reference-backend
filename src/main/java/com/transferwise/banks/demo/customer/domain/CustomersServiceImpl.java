package com.transferwise.banks.demo.customer.domain;

import com.transferwise.banks.demo.credentials.persistence.twprofile.TWProfilePersistence;
import com.transferwise.banks.demo.customer.address.domain.CustomerAddress;
import com.transferwise.banks.demo.customer.address.occupation.domain.Occupation;
import com.transferwise.banks.demo.customer.address.domain.CustomerAddressPersistence;
import com.transferwise.banks.demo.customer.address.occupation.domain.OccupationPersistence;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class CustomersServiceImpl implements CustomersService {

    private final CustomersPersistence customersPersistence;
    private final CustomerAddressPersistence customerAddressPersistence;
    private final OccupationPersistence occupationPersistence;
    private final TWProfilePersistence twProfilePersistence;

    public CustomersServiceImpl(CustomersPersistence customersPersistence, TWProfilePersistence twProfilePersistence, CustomerAddressPersistence customerAddressPersistence, OccupationPersistence occupationPersistence) {
        this.customersPersistence = customersPersistence;
        this.twProfilePersistence = twProfilePersistence;
        this.customerAddressPersistence = customerAddressPersistence;
        this.occupationPersistence = occupationPersistence;
    }

    public Customer find(Long id) {
        Customer customer = customersPersistence.findById(id);

        CustomerAddress customerAddress = customerAddressPersistence.findByCustomerId(id);
        List<Occupation> occupations = occupationPersistence.findByAddressId(customerAddress.getId());
        customer.setCustomerAddress(customerAddress, occupations);


        boolean transferWiseAccountLinked = twProfilePersistence.findByCustomerId(id)
                .isPresent();

        return customer.withTransferWiseAccountLinked(transferWiseAccountLinked);
    }

    public Customer save(Customer customer) {
        return customersPersistence.save(customer);
    }

}
