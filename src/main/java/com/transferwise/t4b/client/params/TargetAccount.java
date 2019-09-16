package com.transferwise.t4b.client.params;

public final class TargetAccount extends Parameter {

    public TargetAccount(final String account) {
        super(account);
    }

    public TargetAccount(final Long account) {
        this(account.toString());
    }

    @Override
    public String key() {
        return "targetAccount";
    }
}