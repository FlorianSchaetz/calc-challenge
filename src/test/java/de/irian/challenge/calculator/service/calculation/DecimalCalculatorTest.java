package de.irian.challenge.calculator.service.calculation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;

import org.assertj.core.data.Offset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import de.irian.challenge.calculator.domain.OperationType;

class DecimalCalculatorTest
{
   private static final Offset<Double> DEFAULT_PRECISION = Offset.offset(1E-6);

   private final DecimalCalculator calculator = new DecimalCalculator();

   @Test
   @DisplayName("Addition must work for Integer and Double")
   void add()
   {
      List<Number> values = List.of(1.1, 2.2, 3);

      final Double result = calculator.calculate(values, OperationType.ADD);

      assertThat(result).isCloseTo(6.3, DEFAULT_PRECISION);
   }

   @Test
   @DisplayName("Subtraction must work for Integer and Double")
   void sub()
   {
      List<Number> values = List.of(1.1, 2, 3.3);

      final Double result = calculator.calculate(values, OperationType.SUB);

      assertThat(result).isCloseTo(-4.2, DEFAULT_PRECISION);
   }

   @Test
   @DisplayName("Multiplication must work for Integer and Double")
   void mul()
   {
      List<Number> values = List.of(1, 2.2, 3.3);

      final Double result = calculator.calculate(values, OperationType.MUL);

      assertThat(result).isEqualTo(7.26);
   }

   @Test
   @DisplayName("Division must work for Integer and Double")
   void div()
   {
      List<Number> values = List.of(1, 2, 3.3);

      final Double result = calculator.calculate(values, OperationType.DIV);

      assertThat(result).isCloseTo(0.151515, DEFAULT_PRECISION);
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
   @DisplayName("Division by zero must throw correct exception")
   void divide_by_zero()
   {
      List<Number> values = List.of(1, 2, 0);

      assertThatThrownBy(() -> calculator.calculate(values, OperationType.DIV))
            .isInstanceOf(ArithmeticException.class)
            .hasMessage("Divide by zero (0.5 / 0.0)");
   }
}