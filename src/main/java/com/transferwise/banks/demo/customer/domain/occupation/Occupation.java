package com.transferwise.banks.demo.customer.domain.occupation;

public class Occupation {

    private Long id;
    private String code;
    private String format;
    private Long addressId;

    public Occupation() {}

    public Occupation(Long id, String code, String format, Long addressId) {
        this.id = id;
        this.code = code;
        this.format = format;
        this.addressId = addressId;
    }

    public Occupation(String code, String format, Long addressId) {
        this.code = code;
        this.format = format;
        this.addressId = addressId;
    }

    public Occupation(String code, String format) {
        this.code = code;
        this.format = format;
    }

    public Long getId() { return id; }
    public String getCode() { return code; }
    public String getFormat() { return format; }
    public Long getAddressId() { return addressId; }

    public void setAddressId(Long addressId) { this.addressId = addressId; }
}
