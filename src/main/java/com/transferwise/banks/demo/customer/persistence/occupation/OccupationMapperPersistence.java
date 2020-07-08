package com.transferwise.banks.demo.customer.persistence.occupation;

import com.transferwise.banks.demo.customer.domain.occupation.Occupation;
import org.springframework.stereotype.Component;

@Component
public class OccupationMapperPersistence {

    public Occupation mapToOccupation(final OccupationEntity occupationEntity) {
        return new Occupation(
                occupationEntity.getCode(),
                occupationEntity.getFormat()
        );
    }

    public OccupationEntity mapToOccupationEntity(final Occupation occupation) {
        return new OccupationEntity(
                occupation.getCode(),
                occupation.getFormat()
        );
    }
}
