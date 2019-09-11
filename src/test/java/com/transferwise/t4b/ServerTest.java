package com.transferwise.t4b;

import com.transferwise.t4b.credentials.CredentialsManager;
import com.transferwise.t4b.customer.CustomersRepository;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.After;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;

import static com.transferwise.t4b.support.FileReader.read;
import static org.mockito.Mockito.mock;

public abstract class ServerTest {

    protected final MockWebServer server = new MockWebServer();
    protected final TestBankConfig config = new TestBankConfig();
    protected final WebClient webClient = WebClient.create(server.url("/").toString());

    protected final CustomersRepository customers = mock(CustomersRepository.class);
    protected final CredentialsManager manager = new CredentialsManager(webClient, config, customers);

    @After
    public void tearDown() throws IOException {
        server.shutdown();
    }

    protected MockResponse response(final String filename) throws IOException {
        return new MockResponse()
                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody(read(filename));
    }
}
