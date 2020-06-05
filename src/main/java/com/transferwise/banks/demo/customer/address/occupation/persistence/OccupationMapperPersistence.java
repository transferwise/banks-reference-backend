package com.transferwise.banks.demo.customer.address.occupation.persistence;

import com.transferwise.banks.demo.customer.address.occupation.domain.Occupation;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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

    public List<Occupation> mapToOccupations(final List<OccupationEntity> occupationEntities) {
        List<Occupation> occupations = new ArrayList<>();

        for (OccupationEntity occupationEntity : occupationEntities) {
            occupations.add(new Occupation(
                    occupationEntity.getId(),
                    occupationEntity.getCode(),
                    occupationEntity.getFormat(),
                    occupationEntity.getAddressId()
            ));
        }

        return occupations;
    }

    public OccupationEntity mapToOccupationEntity(final Occupation occupation) {
        return new OccupationEntity(
                occupation.getCode(),
                occupation.getFormat(),
                occupation.getAddressId()
        );
    }
}
