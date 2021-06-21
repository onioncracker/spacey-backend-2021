package com.heroku.spacey.utils.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class NameCustomValidator implements ConstraintValidator<NameConstraint, String> {

    @Override
    public boolean isValid(String name, ConstraintValidatorContext cxt) {

        return Pattern.compile("(?i)(^[a-zа-яґіїє1-9])((?![ .,'-]$)[a-zа-яґіїє1-9 .,'-]){0,24}$",
                Pattern.UNICODE_CASE).matcher(name).matches();
    }
}