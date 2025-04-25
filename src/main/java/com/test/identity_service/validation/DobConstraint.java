package com.test.identity_service.validation;

import com.test.identity_service.validation.validator.DobConstraintValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = DobConstraintValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.FIELD,ElementType.CONSTRUCTOR,ElementType.PARAMETER})
public @interface DobConstraint {

    String message() default  "Invalid date of birth";

    int min();

    Class<?> [] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
