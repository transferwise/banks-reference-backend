package com.transferwise.banks.demo.credentials.persistence.twprofile;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "tw_profile")
class TWProfileEntity {

    @Id
    private Long twProfileId;
    private Long customerId;
    private String type;

    public TWProfileEntity() {
    }

    public TWProfileEntity(Long twProfileId, Long customerId, String type) {
        this.twProfileId = twProfileId;
        this.customerId = customerId;
        this.type = type;
    }

    public Long getTwProfileId() {
        return twProfileId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public String getType() {
        return type;
    }
}
