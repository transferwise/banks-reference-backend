package com.transferwise.t4b.credentials;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.transferwise.t4b.client.params.RefreshToken;

import javax.persistence.*;
import java.time.ZonedDateTime;

import static java.time.ZonedDateTime.now;

@Entity(name = "credentials")
public class TransferwiseCredentials {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "credentials_generator")
    @SequenceGenerator(name = "credentials_generator", sequenceName = "credentials_id_seq")
    private Long id;

    public final String accessToken;
    public final String refreshToken;
    public final ZonedDateTime expiresIn;

    private TransferwiseCredentials() {
        this(null, null, 0);
    }

    @JsonCreator
    public TransferwiseCredentials(@JsonProperty("access_token") final String accessToken,
                                   @JsonProperty("refresh_token") final String refreshToken,
                                   @JsonProperty("expires_in") final Integer expiresIn) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expiresIn = now().plusSeconds(expiresIn);
    }

    @JsonIgnore
    public boolean areExpired() {
        return now().isAfter(expiresIn);
    }

    public RefreshToken refreshToken() {
        return new RefreshToken(refreshToken);
    }
}
