package com.transferwise.banks.demo.customer.address.domain.twaddress.twoccupation;

import com.transferwise.banks.demo.customer.address.occupation.domain.Occupation;

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
