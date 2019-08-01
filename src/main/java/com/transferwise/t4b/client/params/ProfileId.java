package com.transferwise.t4b.client.params;

public final class ProfileId implements Param {

    public final Long profile;

    public ProfileId(final Long profile) {
        this.profile = profile;
    }

    @Override
    public String key() {
        return "profile";
    }

    @Override
    public String value() {
        return profile.toString();
    }
}
