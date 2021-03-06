package de.irian.challenge.calculator.service.calculation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import de.irian.challenge.calculator.domain.OperationType;

class IntegerCalculatorTest
{
   private final IntegerCalculator calculator = new IntegerCalculator();

   @Test
   @DisplayName("Addition must work for Integer")
   void add()
   {
      List<Number> values = List.of(1, 2, 3);

      final Integer result = calculator.calculate(values, OperationType.ADD);

      assertThat(result).isEqualTo(6);
   }

   @Test
   @DisplayName("Subtraction must work for Integer")
   void sub()
   {
      List<Number> values = List.of(1, 2, 3);

      final Integer result = calculator.calculate(values, OperationType.SUB);

      assertThat(result).isEqualTo(-4);
   }

   @Test
   @DisplayName("Multiplication must work for Integer")
   void mul()
   {
      List<Number> values = List.of(1, 2, 3);

      final Integer result = calculator.calculate(values, OperationType.MUL);

      assertThat(result).isEqualTo(6);
   }

   @Test
   @DisplayName("Division must work for Integer")
   void div()
   {
      List<Number> values = List.of(12, 2, 3);

      final Integer result = calculator.calculate(values, OperationType.DIV);

      assertThat(result).isEqualTo(2);
   }

   @Test
   @DisplayName("Must throw correct exception for empty values list")
   void emptyValues()
   {
      List<Number> values = List.of();

      assertThatThrownBy(() -> calculator.calculate(values, OperationType.ADD))
            .isInstanceOf(ArithmeticException.class)
            .hasMessage("Calculation yielded no result");
   }

   @Test
   @DisplayName("Usage of double must throw correct exception")
   void doubleUsage()
   {
      List<Number> values = List.of(1, 2.2, 3);

      assertThatThrownBy(() -> calculator.calculate(values, OperationType.DIV))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Using a DECIMAL value (2.2) for an INTEGER calculation is not allowed.");
   }

   @Test
   @DisplayName("Division by zero must throw correct exception")
   void divide_by_zero()
   {
      List<Number> values = List.of(1, 2, 0);

      assertThatThrownBy(() -> calculator.calculate(values, OperationType.DIV))
            .isInstanceOf(ArithmeticException.class)
            .hasMessage("Divide by zero (0 / 0)");
   }
}