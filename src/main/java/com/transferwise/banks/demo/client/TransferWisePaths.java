package com.transferwise.banks.demo.client;

import java.util.UUID;

public interface TransferWisePaths {
    String BASE_URL = "https://api.sandbox.transferwise.tech";

    String OAUTH_TOKEN_PATH = "/oauth/token";

    String PROFILES_PATH_V1 = "/v1/profiles";
    String PROFILES_PATH_V2 = "/v2/profiles";
    String RECIPIENTS_PATH_V1 = "/v1/accounts";
    String RECIPIENTS_PATH_V2 = "/v2/accounts";
    String QUOTES_PATH_V1 = "/v1/quotes";
    String QUOTES_PATH_V2 = "/v2/quotes/";

    String SIGNUP_PATH = "/v1/user/signup/registration_code";

    static String recipientRequirementsPath(final UUID quoteId) {
        return QUOTES_PATH_V1 + "/" + quoteId + "/account-requirements";
    }

    static String quotesPathV2(final UUID quoteId) {
        return QUOTES_PATH_V2 + quoteId;
    }

    String TRANSFER_REQUIREMENTS_PATH = "/v1/transfer-requirements";
    String TRANSFERS_PATH = "/v1/transfers";
}
