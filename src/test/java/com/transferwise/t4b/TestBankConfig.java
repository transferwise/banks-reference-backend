package com.transferwise.t4b;

import com.transferwise.t4b.client.TransferWiseBankConfig;
import com.transferwise.t4b.client.params.ClientId;
import com.transferwise.t4b.client.params.RedirectUri;
import org.springframework.util.Base64Utils;

import static java.nio.charset.StandardCharsets.UTF_8;

public class TestBankConfig extends TransferWiseBankConfig {

    @Override
    public ClientId clientId() {
        return new ClientId("client");
    }

    @Override
    public RedirectUri redirectUri() {
        return new RedirectUri("https://localhost:12345/disabled");
    }

    @Override
    public String credentials() {
        return "client:secret";
    }

    @Override
    public String encodedCredentials() {
        return Base64Utils.encodeToString(credentials().getBytes(UTF_8));
    }
}
