package de.irian.challenge.calculator.controller.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * A simple constraint annotation to prevent the user from providing a collection containing nulls
 */
@Documented
@Constraint(validatedBy = NoNullElementsValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface NoNullElements
{
   String message() default "must not contain null elements";

   Class<?>[] groups() default {};

   Class<? extends Payload>[] payload() default {};
}