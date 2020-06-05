package com.transferwise.banks.demo.customer.address.occupation.web;

import javax.validation.constraints.NotNull;

public class NewOccupationRequest {

    @NotNull
    private String code;

    @NotNull
    private String format;

    @NotNull
    private Long addressId;

    public NewOccupationRequest(){}

    public NewOccupationRequest(@NotNull final String code, @NotNull final String format, @NotNull final Long addressId) {
        this.code = code;
        this.format = format;
        this.addressId = addressId;
    }

    public String getCode() { return code; }
    public String getFormat() { return format; }
    public Long getAddressId() {return addressId; }
}
