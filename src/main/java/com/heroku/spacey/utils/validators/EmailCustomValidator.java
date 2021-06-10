package com.heroku.spacey.utils.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailCustomValidator implements ConstraintValidator<EmailConstraint, String> {

    @Override
    public boolean isValid(String email, ConstraintValidatorContext cxt) {
        return email != null && email.matches("^[\\w-.]+@([\\w-]+.)+[\\w-]{2,4}$")
            && (email.length() > 7);
    }
}
