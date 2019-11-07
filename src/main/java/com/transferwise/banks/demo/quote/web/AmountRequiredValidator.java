package com.transferwise.banks.demo.quote.web;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AmountRequiredValidator implements ConstraintValidator<AmountRequired, AnonymousQuoteRequest> {

    @Override
    public boolean isValid(final AnonymousQuoteRequest quoteRequest, final ConstraintValidatorContext context) {
        return quoteRequest.isAmountPresent();
    }
}
