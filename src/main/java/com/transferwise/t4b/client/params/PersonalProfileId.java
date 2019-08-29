package com.transferwise.t4b.client.params;

public final class PersonalProfileId extends Parameter {

    public PersonalProfileId(final Long profile) {
        super(profile.toString());
    }

    @Override
    public String key() {
        return "profile";
    }
}
