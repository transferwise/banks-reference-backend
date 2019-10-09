package com.transferwise.banks.demo.client.params;

public final class ProfileId extends Parameter {

    public ProfileId(final Long profile) {
        super(profile.toString());
    }

    @Override
    public String key() {
        return "profile";
    }
}
