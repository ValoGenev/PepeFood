package com.gegessen.validation;


import com.gegessen.model.UserType;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD, METHOD,PARAMETER })
@Retention(RUNTIME)
@Constraint(validatedBy = UserTypeCategoryValidator.class)
@Documented
public @interface ValidUserTypeCategoryValidator {

    String message() default "NOT A VALID USERTYPE CATEGORY. "+
            "Values accepted [GOOGLE,EMAIL,FACEBOOK]";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
