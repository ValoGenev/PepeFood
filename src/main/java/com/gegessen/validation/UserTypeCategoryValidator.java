package com.gegessen.validation;

import com.gegessen.model.UserType;
import org.apache.commons.lang3.EnumUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserTypeCategoryValidator implements ConstraintValidator<ValidUserTypeCategoryValidator,String> {

    @Override
    public boolean isValid(String category, ConstraintValidatorContext constraintValidatorContext) {
        return EnumUtils.isValidEnum(UserType.class, category);
    }

    @Override
    public void initialize(ValidUserTypeCategoryValidator constraintAnnotation) {

    }
}
