package com.transferwise.banks.demo.customer.domain;

import com.transferwise.banks.demo.credentials.persistence.twprofile.TWProfilePersistence;
import com.transferwise.banks.demo.customer.domain.address.Address;
import com.transferwise.banks.demo.customer.domain.occupation.Occupation;
import com.transferwise.banks.demo.customer.domain.address.AddressPersistence;
import com.transferwise.banks.demo.customer.domain.occupation.OccupationPersistence;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
class CustomersServiceImpl implements CustomersService {

    private final CustomersPersistence customersPersistence;
    private final AddressPersistence addressPersistence;
    private final OccupationPersistence occupationPersistence;
    private final TWProfilePersistence twProfilePersistence;

    public CustomersServiceImpl(CustomersPersistence customersPersistence, TWProfilePersistence twProfilePersistence, AddressPersistence addressPersistence, OccupationPersistence occupationPersistence) {
        this.customersPersistence = customersPersistence;
        this.twProfilePersistence = twProfilePersistence;
        this.addressPersistence = addressPersistence;
        this.occupationPersistence = occupationPersistence;
    }

    public Customer find(Long id) {
        Customer customer = customersPersistence.findById(id);

        Address address = addressPersistence.findByCustomerId(id);

        if(address.getId() != null) {
            List<Occupation> occupations = occupationPersistence.findByAddressId(address.getId());
            customer.setCustomerAddress(address, occupations);
        }

        boolean transferWiseAccountLinked = twProfilePersistence.findByCustomerId(id)
                .isPresent();

        return customer.withTransferWiseAccountLinked(transferWiseAccountLinked);
    }

    public Customer save(Customer customer) {
        Customer cst = customersPersistence.save(customer);

        if(cst.getId() != null){
            customer.getAddress().setCustomerId(cst.getId());
            Address address = addressPersistence.save(customer.getAddress());

            if(address.getId() != null) {
                List<Occupation> occupations = new ArrayList<>();
                for(Occupation occupation : customer.getAddress().getOccupations()) {
                    occupation.setAddressId(address.getId());
                    occupations.add(occupationPersistence.save(occupation));
                }

                cst.setCustomerAddress(address, occupations);
            }
        }

        return cst;
    }

}
