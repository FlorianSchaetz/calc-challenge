package de.irian.challenge.calculator.service.calculation;

import java.util.List;
import java.util.Objects;

import de.irian.challenge.calculator.domain.OperationType;

/**
 * A simple abstract base class for all Calculators to avoid repetition
 *
 * @param <T> The specific type this calculator will work with and return, should be json-serializable in this ApplicationContext
 */
public abstract class AbstractCalculator<T extends Number> implements Calculator
{
   @Override
   public T calculate(final List<Number> values, final OperationType operationType)
   {
      return values.stream()
            .filter(Objects::nonNull) // This should not be required (because of @NoNullElements in the CalculationRequest), but better safe than sorry
            .map(this::convert)
            .reduce((left, right) -> calculate(left, right, operationType))
            .orElseThrow(() -> new ArithmeticException("Calculation yielded no result"));
   }

   /**
    * This method must be implemented to convert any given Number into the target number type
    *
    * @param number The number to convert (can be Double or Integer), will never be null, but can be zero or negative
    * @return The number represented in the target number type, may never be null (otherwise error must be handled in the calculate method below)
    */
   public abstract T convert(Number number);

   /**
    * This method must be implemented to do a calculation of the given OperationType on the two given numeric parameters
    *
    * @param left  The left side of the calculation
    * @param right The right side of the calculation
    * @param type  The OperationType for this calculation
    * @return The result represented in the target number type, may never be null
    */
   public abstract T calculate(T left, T right, OperationType type);
}
