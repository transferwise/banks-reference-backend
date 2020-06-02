package com.transferwise.banks.demo.customer.address.occupation.persistence;

import com.transferwise.banks.demo.customer.address.occupation.domain.Occupation;
import org.springframework.stereotype.Component;

@Component
public class OccupationMapperPersistence {

    public Occupation mapToOccupation(final OccupationEntity occupationEntity) {
        return new Occupation(
                occupationEntity.getId(),
                occupationEntity.getCode(),
                occupationEntity.getFormat(),
                occupationEntity.getAddressId()
        );
    }

    public OccupationEntity mapToOccupationEntity(final Occupation occupation) {
        return new OccupationEntity(
                occupation.getCode(),
                occupation.getFormat(),
                occupation.getAddressId()
        );
    }
}
