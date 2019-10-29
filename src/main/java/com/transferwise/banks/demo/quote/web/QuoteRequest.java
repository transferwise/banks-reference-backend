package com.transferwise.banks.demo.quote.web;

import com.transferwise.banks.demo.values.Profile;
import com.transferwise.banks.demo.values.SourceAmount;
import com.transferwise.banks.demo.values.SourceCurrency;
import com.transferwise.banks.demo.values.TargetAmount;
import com.transferwise.banks.demo.values.TargetCurrency;

@AmountRequired
class QuoteRequest extends AnonymousQuoteRequest {

    private Profile profile;

    public QuoteRequest() {
        super();
    }

    public QuoteRequest(final Profile profile, final SourceCurrency sourceCurrency, final TargetCurrency targetCurrency,
                        final SourceAmount sourceAmount, final TargetAmount targetAmount) {
        this();
        this.profile = profile;
    }

    public Profile getProfile() {
        return profile;
    }
}
