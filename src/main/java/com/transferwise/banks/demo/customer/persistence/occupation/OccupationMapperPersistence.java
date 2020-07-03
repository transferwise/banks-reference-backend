package com.transferwise.banks.demo.customer.persistence.occupation;

import com.transferwise.banks.demo.customer.domain.occupation.Occupation;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OccupationMapperPersistence {

    public Occupation mapToOccupation(final OccupationEntity occupationEntity) {
        return new Occupation(
                occupationEntity.getCode(),
                occupationEntity.getFormat()
        );
    }

    public List<Occupation> mapToOccupations(final List<OccupationEntity> occupationEntities) {
        List<Occupation> occupations = new ArrayList<>();

        for (OccupationEntity occupationEntity : occupationEntities) {
            occupations.add(new Occupation(
                    occupationEntity.getCode(),
                    occupationEntity.getFormat()
            ));
        }

        return occupations;
    }

    public OccupationEntity mapToOccupationEntity(final Occupation occupation) {
        return new OccupationEntity(
                occupation.getCode(),
                occupation.getFormat()
        );
    }
}
