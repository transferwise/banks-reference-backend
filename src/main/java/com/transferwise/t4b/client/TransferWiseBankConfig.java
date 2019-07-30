package com.transferwise.t4b.client;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.Base64Utils;

import static java.nio.charset.StandardCharsets.UTF_8;

@ConfigurationProperties("twbank")
public class TransferWiseBankConfig {
    private String clientId;
    private String secret;
    private String redirectUri;

    public void setClientId(final String clientId) {
        this.clientId = clientId;
    }

    public void setSecret(final String secret) {
        this.secret = secret;
    }

    public void setRedirectUri(final String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public String clientId() {
        return clientId;
    }

    public String redirectUri() {
        return redirectUri;
    }

    public String encodedCredentials() {
        final String credentials = clientId + ":" + secret;
        return Base64Utils.encodeToString(credentials.getBytes(UTF_8));
    }
}
