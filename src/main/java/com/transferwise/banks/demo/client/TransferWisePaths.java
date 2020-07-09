package com.transferwise.banks.demo.client;

import java.util.UUID;

public interface TransferWisePaths {

    String OAUTH_TOKEN_PATH = "/oauth/token";

    String PROFILES_PATH_V1 = "/v1/profiles";
    String PROFILES_PATH_V2 = "/v2/profiles";
    String RECIPIENTS_PATH_V1 = "/v1/accounts";
    String RECIPIENTS_PATH_V2 = "/v2/accounts";
    String QUOTES_PATH_V1 = "/v1/quotes";
    String QUOTES_PATH_V2 = "/v2/quotes/";

    String SIGNUP_PATH = "/v1/user/signup/registration_code";

    String TRANSFER_REQUIREMENTS_PATH = "/v1/transfer-requirements";
    String TRANSFERS_PATH = "/v1/transfers";

<<<<<<< HEAD
=======
    String ADDRESS_PATH = "/v1/addresses";

>>>>>>> 80005fdfa15e98a1dde8987b902e5886f92589cc
    static String recipientRequirementsPath(final UUID quoteId) {
        return QUOTES_PATH_V1 + "/" + quoteId + "/account-requirements";
    }

    static String recipientByIdPath(final Long recipientId) {
        return RECIPIENTS_PATH_V2 + "/" + recipientId;
    }

    static String quotesPathV2(final UUID quoteId) {
        return QUOTES_PATH_V2 + quoteId;
    }

    static String getProfilePath(final Long profileId) {
        return PROFILES_PATH_V1 + "/" + profileId;
    }

    static String updateWindowPath(final Long profileId) {
        return PROFILES_PATH_V1 + "/" + profileId + "/update-window";
    }
<<<<<<< HEAD
=======

    static String getAddressByIdPath(final Long addressId) { return ADDRESS_PATH + "/" + addressId; }
>>>>>>> 80005fdfa15e98a1dde8987b902e5886f92589cc
}
