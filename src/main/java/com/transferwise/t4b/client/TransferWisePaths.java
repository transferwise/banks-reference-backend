package com.transferwise.t4b.client;

import java.util.UUID;

interface TransferWisePaths {
    String BASE_URL = "https://api.sandbox.transferwise.tech";

    String OAUTH_TOKEN_PATH = "/oauth/token";

    String PROFILES_PATH_V1 = "/v1/profiles";
    String PROFILES_PATH_V2 = "/v2/profiles";
    String ACCOUNTS_PATH = "/v1/accounts";
    String QUOTES_PATH_V1 = "/v1/quotes";
    String QUOTES_PATH_V2 = "/v2/quotes/";

    String SIGNUP_PATH = "/v1/user/signup/registration_code";

    static String recipientRequirementsPath(final UUID quoteId) {
        return QUOTES_PATH_V1 + "/" + quoteId + "/account-requirements";
    }
}
