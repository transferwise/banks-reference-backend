package com.transferwise.banks.demo.transfer.web.status;

import com.fasterxml.jackson.annotation.JsonProperty;

class TransferStatusChangeResource {

    private static final String TYPE = "transfer";

    private Long id;

    @JsonProperty("profile_id")
    private Long profileId;

    @JsonProperty("account_id")
    private Long accountId;

    public String getType() {
        return TYPE;
    }

    public Long getId() {
        return id;
    }

    public Long getProfileId() {
        return profileId;
    }

    public Long getAccountId() {
        return accountId;
    }

    @Override
    public String toString() {
        return "TransferStatusChangeResource{" +
                "id=" + id +
                ", profileId=" + profileId +
                ", accountId=" + accountId +
                '}';
    }
}
