package com.transferwise.banks.demo.credentials.persistence.twprofile;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity(name = "tw_profile")
class TWProfileEntity {

    @Id
    private Long twProfileId;
    private Long customerId;
    private String type;
    private LocalDateTime updatedAt;

    public TWProfileEntity() {
    }

    public TWProfileEntity(Long twProfileId, Long customerId, String type, LocalDateTime updatedAt) {
        this.twProfileId = twProfileId;
        this.customerId = customerId;
        this.type = type;
        this.updatedAt = updatedAt;
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

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
