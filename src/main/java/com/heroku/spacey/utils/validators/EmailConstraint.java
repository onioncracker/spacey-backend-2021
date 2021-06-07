package com.heroku.spacey.utils.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EmailCustomValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailConstraint {
    String message() default "Wrong email format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}