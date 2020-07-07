package com.transferwise.banks.demo.customer.persistence.occupation;

import javax.persistence.*;

@Entity(name = "customer_occupations")
public class OccupationEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String code;
    private String format;

    private Long addressId;

    public OccupationEntity() {}

    public OccupationEntity(String code, String format) {
        this.code = code;
        this.format = format;
    }

    public Long getId() { return id; }
    public String getCode() { return code; }
    public String getFormat() { return format; }
}
