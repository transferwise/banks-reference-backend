package com.transferwise.banks.demo.credentials.domain.twaddress.twoccupation;

import com.transferwise.banks.demo.customer.domain.occupation.Occupation;

public class TWOccupation {

    private String code;
    private String format;

    public TWOccupation() {}

    public TWOccupation(Occupation occupation) {
        this.code = occupation.getCode();
        this.format = occupation.getFormat();
    }

    public String getCode() { return code; }
    public String getFormat() { return format; }
}
