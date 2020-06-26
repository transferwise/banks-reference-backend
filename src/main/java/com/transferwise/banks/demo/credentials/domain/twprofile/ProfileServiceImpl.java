package com.transferwise.banks.demo.credentials.domain.twprofile;

import com.transferwise.banks.demo.credentials.domain.TWUserTokens;
import com.transferwise.banks.demo.credentials.persistence.twprofile.TWProfilePersistence;
import com.transferwise.banks.demo.credentials.domain.twaddress.TWAddressService;
import com.transferwise.banks.demo.customer.domain.Customer;
import com.transferwise.banks.demo.customer.domain.CustomersPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.format.DateTimeFormatter;

import static java.time.LocalDateTime.now;

@Component
class ProfileServiceImpl implements ProfileService {

    private static final Logger log = LoggerFactory.getLogger(ProfileServiceImpl.class);

    private static final String PERSONAL = "personal";

    private final TWProfilePersistence twProfilePersistence;
    private final CustomersPersistence customersPersistence;
    private final ProfileTWClient profileTWClient;
    private final TWProfileComparator twProfileComparator;
    private final TWAddressService twAddressService;

    ProfileServiceImpl(TWProfilePersistence twProfilePersistence, CustomersPersistence customersPersistence, ProfileTWClient profileTWClient, TWProfileComparator twProfileComparator, TWAddressService twAddressService) {
        this.twProfilePersistence = twProfilePersistence;
        this.customersPersistence = customersPersistence;
        this.profileTWClient = profileTWClient;
        this.twProfileComparator = twProfileComparator;
        this.twAddressService = twAddressService;
    }


    @Override
    public Mono<TWProfile> getPersonalProfile(TWUserTokens twUserTokens) {
        return profileTWClient.getProfiles(twUserTokens)
                .filter(twProfile -> PERSONAL.equalsIgnoreCase(twProfile.getType()))
                .next();
    }

    @Override
    public Mono<TWProfile> createPersonalProfile(final TWUserTokens twUserTokens, final Customer customer) {
        return profileTWClient.createPersonalProfile(twUserTokens, buildCreatePersonalProfile(customer))
                .doOnSuccess(twProfile -> twAddressService.createAddress(twUserTokens, twProfile));
    }

    @Override
    public void performRefreshCycle(final TWUserTokens twUserTokens, final Long customerId) {
        log.info("Checking if we need to perform yearly update for customer {}", customerId);

        twProfilePersistence.findByCustomerId(customerId)
                .filter(TWProfile::needsYearlyUpdate)
                .ifPresent(currentPersistedTwProfile -> {
                    final Long twProfileId = currentPersistedTwProfile.getTwProfileId();
                    log.info("Customer {} with twProfileId {} hasn't been updated in more than 1 year", customerId, twProfileId);
                    refreshCycleOperations(twUserTokens, customerId, currentPersistedTwProfile, twProfileId);
                });
    }

    private void refreshCycleOperations(final TWUserTokens twUserTokens, final Long customerId, final TWProfile currentPersistedTwProfile, final Long twProfileId) {
        profileTWClient.openUpdateWindow(twUserTokens, twProfileId)
                .subscribe(isOpen -> {
                    Customer customer = customersPersistence.findById(customerId);

                    profileTWClient.getProfile(twUserTokens, twProfileId)
                            .flatMap(twProfileFromTw -> extractTwProfileToPersist(twUserTokens, currentPersistedTwProfile, twProfileId, customer, twProfileFromTw))
                            .subscribe(twProfileToPersist -> persistTwProfileAndCloseWindow(twUserTokens, twProfileId, twProfileToPersist));
                });
    }

    private Mono<TWProfile> extractTwProfileToPersist(final TWUserTokens twUserTokens, final TWProfile currentPersistedTwProfile, final Long twProfileId, final Customer customer, final TWProfile twProfileFromTw) {
        if (checkUpdateNeeded(twProfileFromTw, customer)) {
            return updatePersonalProfile(twUserTokens, customer, twProfileId);
        } else {
            return Mono.just(currentPersistedTwProfile);
        }
    }

    private Mono<TWProfile> updatePersonalProfile(final TWUserTokens twUserTokens, final Customer customer, final Long twProfileId) {
        log.info("Updating customer {} profile", customer.getId());
        return profileTWClient.updatePersonalProfile(twUserTokens, buildUpdatePersonalProfile(customer, twProfileId));
    }

    private Boolean checkUpdateNeeded(final TWProfile twProfile, final Customer customer) {
        return !twProfileComparator.isTwProfileEqualToCustomer(twProfile, customer);
    }

    private void persistTwProfileAndCloseWindow(TWUserTokens twUserTokens, Long twProfileId, TWProfile twProfileToPersist) {
        twProfilePersistence.save(twProfileToPersist.withUpdatedAt(now()));
        profileTWClient.closeUpdateWindow(twUserTokens, twProfileId);
    }

    private CreatePersonalProfile buildCreatePersonalProfile(final Customer customer) {
        ProfileDetails profileDetails = new ProfileDetails(customer.getFirstName(),
                customer.getLastName(),
                customer.getDateOfBirth().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                customer.getPhoneNumber());

        return new CreatePersonalProfile("personal", profileDetails);
    }

    private UpdatePersonalProfile buildUpdatePersonalProfile(final Customer customer, final Long twProfileId) {
        ProfileDetails profileDetails = new ProfileDetails(customer.getFirstName(),
                customer.getLastName(),
                customer.getDateOfBirth().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                customer.getPhoneNumber());

        return new UpdatePersonalProfile(twProfileId, "personal", profileDetails);
    }

}
