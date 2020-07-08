package com.transferwise.banks.demo.customer.web.occupation;

import javax.validation.constraints.NotNull;

public class NewOccupationRequest {

    @NotNull
    private String code;

    @NotNull
    private String format;

    public NewOccupationRequest(){}

    public NewOccupationRequest(@NotNull final String code, @NotNull final String format) {
        this.code = code;
        this.format = format;
    }

    public String getCode() { return code; }
    public String getFormat() { return format; }
}
