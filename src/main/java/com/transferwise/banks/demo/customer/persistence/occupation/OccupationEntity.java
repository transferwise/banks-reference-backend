package com.transferwise.banks.demo.customer.persistence.occupation;

import com.transferwise.banks.demo.customer.persistence.address.AddressEntity;

import javax.persistence.*;

@Entity(name = "customer_occupations")
public class OccupationEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String code;
    private String format;

    //private long addressId;

    @ManyToOne
    @JoinColumn(name = "address_id", insertable = false, updatable = false)
    private AddressEntity address;

    public OccupationEntity() {}

    /*public OccupationEntity(String code, String format, Long addressId) {
        this.code = code;
        this.format = format;
        this.addressId = addressId;
    }*/

    public OccupationEntity(String code, String format) {
        this.code = code;
        this.format = format;
    }

    public Long getId() { return id; }
    public String getCode() { return code; }
    public String getFormat() { return format; }
    //public Long getAddressId() { return addressId; }
}
