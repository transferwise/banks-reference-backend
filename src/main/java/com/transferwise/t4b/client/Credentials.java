package com.transferwise.t4b.client;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
public class Credentials {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "credentials_generator")
    @SequenceGenerator(name = "credentials_generator", sequenceName = "credentials_id_seq")
    private Long id;

    public final String accessToken;
    public final String refreshToken;

    private Credentials() {
        this(null, null);
    }

    @JsonCreator
    public Credentials(@JsonProperty("access_token") final String accessToken,
                       @JsonProperty("refresh_token") final String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    @JsonIgnore
    public boolean isEmpty() {
        return accessToken == null && refreshToken == null;
    }
}
