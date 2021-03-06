package de.irian.challenge.calculator.controller.validation;

import java.util.Collection;
import java.util.Objects;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * The validator for the @NoNullElements constraint (valid if the collection is not null and does not contain any nulls)
 */
public class NoNullElementsValidator implements ConstraintValidator<NoNullElements, Collection<?>>
{
   @Override
   public boolean isValid(final Collection<?> value, final ConstraintValidatorContext context)
   {
      return value != null && value.stream().allMatch(Objects::nonNull);
   }
}
