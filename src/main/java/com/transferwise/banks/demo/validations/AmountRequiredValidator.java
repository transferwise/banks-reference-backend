package com.transferwise.banks.demo.validations;

import com.transferwise.banks.demo.quote.QuoteRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AmountRequiredValidator implements ConstraintValidator<AmountRequired, QuoteRequest> {

    @Override
    public boolean isValid(final QuoteRequest quoteRequest, final ConstraintValidatorContext context) {
        return quoteRequest.isAmountPresent();
    }
}
