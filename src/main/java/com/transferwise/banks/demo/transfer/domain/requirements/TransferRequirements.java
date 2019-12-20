package com.transferwise.banks.demo.transfer.domain.requirements;

import java.util.List;

public class TransferRequirements {

    private List<TransferRequirement> transferRequirements;

    public TransferRequirements() {
    }

    public TransferRequirements(List<TransferRequirement> transferRequirements) {
        this.transferRequirements = transferRequirements;
    }

    public List<TransferRequirement> getTransferRequirements() {
        return transferRequirements;
    }

    public Field getFieldByKey(final String key){
        return transferRequirements.stream().flatMap(t -> t.getFields().stream())
                .filter(f -> isReferenceField(f, key)).findFirst().orElse(null);
    }

    private boolean isReferenceField(final Field field, final String key) {
        return field.getGroup().stream().anyMatch(f -> key.equals(f.getKey()));
    }
}
