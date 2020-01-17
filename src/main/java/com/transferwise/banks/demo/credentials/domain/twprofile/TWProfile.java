package com.transferwise.banks.demo.credentials.domain.twprofile;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

public class TWProfile {

    private Long twProfileId;
    private Long customerId;
    private String type;
    private ProfileDetails profileDetails;
    private LocalDateTime updatedAt;

    public TWProfile() {
    }

    public TWProfile(Long twProfileId, Long customerId, String type, ProfileDetails profileDetails) {
        this.twProfileId = twProfileId;
        this.customerId = customerId;
        this.type = type;
        this.profileDetails = profileDetails;
    }

    public TWProfile(Long twProfileId, Long customerId, String type, LocalDateTime updatedAt) {
        this.twProfileId = twProfileId;
        this.customerId = customerId;
        this.type = type;
        this.updatedAt = updatedAt;
    }

    public TWProfile(Long twProfileId, Long customerId, String type, ProfileDetails profileDetails, LocalDateTime updatedAt) {
        this.twProfileId = twProfileId;
        this.customerId = customerId;
        this.type = type;
        this.profileDetails = profileDetails;
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

    public ProfileDetails getProfileDetails() {
        return profileDetails;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public TWProfile withUpdatedAt(final LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public boolean needsYearlyUpdate() {
        return updatedAt == null || updatedAt.isBefore(now().minusYears(1L));
    }
}
