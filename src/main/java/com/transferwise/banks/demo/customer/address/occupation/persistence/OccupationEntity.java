package com.transferwise.banks.demo.customer.address.occupation.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "customer_occupations")
class OccupationEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String code;
    private String format;

    private long addressId;

    public OccupationEntity() {}

    public OccupationEntity(Long id, String code, String format, Long addressId){ }

    public OccupationEntity(String code, String format, Long addressId) {
        this.code = code;
        this.format = format;
        this.addressId = addressId;
    }

    public Long getId() { return id; }
    public String getCode() { return code; }
    public String getFormat() { return format; }
    public Long getAddressId() { return addressId; }
}
