package com.transferwise.t4b.client;

import com.transferwise.t4b.Fabricator;
import com.transferwise.t4b.TestBankConfig;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.After;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;

import static com.transferwise.t4b.support.FileReader.read;
import static org.junit.Assert.*;

public class ApiClientTest {

    private final MockWebServer server = new MockWebServer();

    private final TestBankConfig config = new TestBankConfig();
    private final Authorizations auth = new Authorizations(config);
    private final ApiClient client = new ApiClient(WebClient.create(server.url("/").toString()), config, auth, manager);

    @After
    public void tearDown() throws IOException {
        server.shutdown();
    }

    @Test
    public void findRecipientsForCustomer() throws IOException {
        server.enqueue(response("recipients.json"));

        final var recipient = client.recipients(Fabricator.newCustomer()).blockFirst();

        assertEquals("GBP", recipient.getCurrency());
        assertTrue(recipient.isActive());
    }

    @Test
    public void createAllCredentialsForNewCustomer() throws IOException {
        server.enqueue(response("client-credentials.json"));
        server.enqueue(response("user.json"));
        server.enqueue(response("user-credentials.json"));
        server.enqueue(response("profile.json"));

        final var customer = client.userCredentials(Fabricator.newCustomer()).block();
        assertNotNull(customer.getCredentials());
        assertNotNull(customer.getUser());
        assertNotNull(customer.getProfile());
    }

    private MockResponse response(final String filename) throws IOException {
        return new MockResponse()
                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody(read(filename));
    }
}
