package com.transferwise.banks.demo.customer.address.web;

import com.transferwise.banks.demo.customer.address.occupation.domain.Occupation;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

public class NewAddressRequest {

    @NotNull
    private String firstLine;

    @NotNull
    private String postCode;

    @NotNull
    private String city;

    private String state;

    @NotNull
    @Pattern(regexp = "/^A[^ABCHJKNPVY]|B[^CKPUX]|C[^BEJPQST]|D[EJKMOZ]|E[CEGHRST]" +
            "|F[IJKMOR]|G[^CJKOVXZ]|H[KMNRTU]|I[DEL-OQ-T]|J[EMOP]|K[EGHIMNPRWYZ]" +
            "|L[ABCIKR-VY]|M[^BIJ]|N[ACEFGILOPRUZ]|OM|P[AE-HK-NRSTWY]|QA|R[EOSUW]|S[^FPQUW]" +
            "|T[^ABEIPQSUXY]|U[AGMSYZ]|V[ACEGINU]|WF|WS|YE|YT|Z[AMW]$/ix",
            message = "Please enter a valid ISO 3166-1 alpha-2 code")
    private String country;

    @NotNull
    private Long customerId;

    private List<Occupation> occupations;

    public NewAddressRequest(){}

    public NewAddressRequest(@NotNull final String firstLine, @NotNull final String postCode, @NotNull final String city, final String state, @NotNull final String  country, @NotNull final Long customerId, final List<Occupation> occupations) {
        this.firstLine = firstLine;
        this.postCode = postCode;
        this.city = city;
        this.state = state;
        this.country = country;
        this.customerId = customerId;
        this.occupations = occupations;
    }

    public String getFirstLine() { return firstLine; }
    public String getPostCode() { return postCode; }
    public String getCity() { return city; }
    public String getState() { return state; }
    public String getCountry() { return country; }
    public Long getCustomerId() { return customerId; }
    public List<Occupation> getOccupations() { return occupations; }
}
