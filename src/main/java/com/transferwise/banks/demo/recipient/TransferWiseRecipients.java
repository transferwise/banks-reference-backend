package com.transferwise.banks.demo.recipient;

import com.transferwise.banks.demo.credentials.domain.CredentialsManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class TransferWiseRecipients {

    private static final Logger log = LoggerFactory.getLogger(TransferWiseRecipients.class);

    private static final String TYPE = "type";
    private static final String EMAIL = "email";

    private final WebClient client;
    private final CredentialsManager manager;

    public TransferWiseRecipients(final WebClient client, final CredentialsManager manager) {
        this.client = client;
        this.manager = manager;
    }

    /*public Mono<Recipient> getRecipient(final CustomerEntity customerEntity, final Long recipientId) {
        return manager.credentialsFor(customerEntity).flatMap(credentials ->
                client.get()
                        .uri(recipientByIdPath(recipientId))
                        .header(AUTHORIZATION, credentials.bearer())
                        .retrieve()
                        .bodyToMono(Recipient.class));
    }*/

    /*public Flux<Recipient> all(final CustomerEntity customerEntity) {
        return manager.credentialsFor(customerEntity).flatMapMany(credentials ->
                client.get()
                        .uri(new UriWithParams(RECIPIENTS_PATH_V2, customerEntity.profileId()))
                        .header(AUTHORIZATION, credentials.bearer())
                        .retrieve()
                        .bodyToFlux(TWRecipientsContent.class)
                        .flatMap(content -> fromIterable(content.recipients)));
    }*/

    /*public Mono<List<Map>> requirements(final CustomerEntity customerEntity, final UUID quoteId) {
        return manager.credentialsFor(customerEntity).flatMap(credentials ->
                client.get()
                        .uri(recipientRequirementsPath(quoteId))
                        .header(AUTHORIZATION, credentials.bearer())
                        .retrieve()
                        .bodyToMono(String.class))
                .map(json -> {
                    List<Map> accountRequirements = new ArrayList<>();

                    try {
                        ObjectMapper mapper = new ObjectMapper();
                        accountRequirements = mapper.readValue(json, new TypeReference<List<Map>>() {
                        });

                        accountRequirements.removeIf(requirement -> EMAIL.equals(requirement.get(TYPE)));

                    } catch (IOException e) {
                        log.error("Error while parsing account requirements json for quoteId {}", quoteId, e);
                    }

                    return accountRequirements;
                });
    }*/

    /*public Mono<String> requirements(final CustomerEntity customerEntity, final String bodyRequest, final UUID quoteId) {
        return manager.credentialsFor(customerEntity).flatMap(credentials ->
                client.post()
                        .uri(recipientRequirementsPath(quoteId))
                        .header(AUTHORIZATION, credentials.bearer())
                        .contentType(APPLICATION_JSON_UTF8)
                        .body(fromObject(bodyRequest))
                        .retrieve()
                        .bodyToMono(String.class));
    }
*/
    /*public Mono<String> create(final CustomerEntity customerEntity, final String bodyRequest) {
        return manager.credentialsFor(customerEntity).flatMap(credentials ->
                client.post()
                        .uri(RECIPIENTS_PATH_V1)
                        .header(AUTHORIZATION, credentials.bearer())
                        .contentType(APPLICATION_JSON_UTF8)
                        .body(fromObject(bodyRequest))
                        .retrieve()
                        .bodyToMono(String.class));
    }*/
}
