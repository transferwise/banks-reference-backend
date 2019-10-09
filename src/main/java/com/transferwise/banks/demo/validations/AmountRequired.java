package com.transferwise.banks.demo.validations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(TYPE)
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = AmountRequiredValidator.class)
public @interface AmountRequired {

    String message() default "source amount or target amount must be provided";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
