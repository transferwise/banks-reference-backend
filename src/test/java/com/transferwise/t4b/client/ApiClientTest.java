package com.transferwise.t4b.client;

import com.transferwise.t4b.TestBankConfig;
import com.transferwise.t4b.credentials.CredentialsManager;
import com.transferwise.t4b.customer.Customer;
import com.transferwise.t4b.customer.CustomersRepository;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.After;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;

import static com.transferwise.t4b.Fabricator.newCustomer;
import static com.transferwise.t4b.support.FileReader.read;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ApiClientTest {

    private final MockWebServer server = new MockWebServer();
    private final TestBankConfig config = new TestBankConfig();
    private final WebClient webClient = WebClient.create(server.url("/").toString());

    private final CustomersRepository customers = mock(CustomersRepository.class);

    private final CredentialsManager manager = new CredentialsManager(webClient, config, customers);
    private final ApiClient client = new ApiClient(webClient, config, manager);

    @After
    public void tearDown() throws IOException {
        server.shutdown();
    }

    @Test
    public void findRecipientsForCustomer() throws IOException {
        final var customer = newCustomer();

        when(customers.save(any(Customer.class))).thenReturn(customer);
        server.enqueue(response("recipients.json"));

        final var recipient = client.recipients(customer).blockFirst();

        assertEquals("GBP", recipient.getCurrency());
        assertTrue(recipient.isActive());
    }

    @Test
    public void createAllCredentialsForNewCustomer() throws IOException {
        server.enqueue(response("client-credentials.json"));
        server.enqueue(response("user.json"));
        server.enqueue(response("user-credentials.json"));
        server.enqueue(response("profile.json"));

        final var customer = client.userCredentials(newCustomer()).block();
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
