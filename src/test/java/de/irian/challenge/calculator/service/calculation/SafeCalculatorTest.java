package de.irian.challenge.calculator.service.calculation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import de.irian.challenge.calculator.domain.OperationType;

class SafeCalculatorTest
{
   private final SafeCalculator calculator = new SafeCalculator();

   @Test
   @DisplayName("Addition must work for Integer and Double")
   void add()
   {
      List<Number> values = List.of(1, 2.2, 3);

      final BigDecimal result = calculator.calculate(values, OperationType.ADD);

      assertThat(result).isEqualTo(BigDecimal.valueOf(6.2));
   }

   @Test
   @DisplayName("Subtraction must work for Integer and Double")
   void sub()
   {
      List<Number> values = List.of(1, 2.2, 3);

      final BigDecimal result = calculator.calculate(values, OperationType.SUB);

      assertThat(result).isEqualTo(BigDecimal.valueOf(-4.2));
   }

   @Test
   @DisplayName("Multiplication must work for Integer and Double")
   void mul()
   {
      List<Number> values = List.of(1, 2.2, 3);

      final BigDecimal result = calculator.calculate(values, OperationType.MUL);

      assertThat(result).isEqualTo(BigDecimal.valueOf(6.6));
   }

   @Test
   @DisplayName("Division must work for Integer and Double")
   void div()
   {
      List<Number> values = List.of(1, 2.2, 3);

      final BigDecimal result = new SafeCalculator().calculate(values, OperationType.DIV);

      assertThat(result).isEqualTo(new BigDecimal("0.1515151515151515151515151515151515151515151515151515151515151515151515151515151515151515151515151515151515151515151515151515"));
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
      List<Number> values = List.of(1.1, 2.2, 0);

      assertThatThrownBy(() -> calculator.calculate(values, OperationType.DIV))
            .isInstanceOf(ArithmeticException.class)
            .hasMessage("Divide by zero (0.5 / 0)");
   }
}