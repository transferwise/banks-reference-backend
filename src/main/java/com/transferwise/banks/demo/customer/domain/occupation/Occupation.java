package com.transferwise.banks.demo.customer.domain.occupation;

public class Occupation {

    private String code;
    private String format;
    private Long addressId;

    public Occupation() {}

    public Occupation(String code, String format, Long addressId) {
        this.code = code;
        this.format = format;
        this.addressId = addressId;
    }

    public Occupation(String code, String format) {
        this.code = code;
        this.format = format;
    }

    public String getCode() { return code; }
    public String getFormat() { return format; }
}
