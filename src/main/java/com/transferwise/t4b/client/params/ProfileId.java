package com.transferwise.t4b.client.params;

public final class ProfileId extends Parameter {

    public ProfileId(final Long profile) {
        super(profile.toString());
    }

    @Override
    public String key() {
        return "profile";
    }
}
