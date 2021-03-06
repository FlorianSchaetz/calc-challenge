package de.irian.challenge.calculator.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import de.irian.challenge.calculator.domain.OperationType;
import de.irian.challenge.calculator.domain.ResultType;
import de.irian.challenge.calculator.service.calculation.Calculator;

@ExtendWith(MockitoExtension.class)
class CalculatorServiceTest
{
   @Mock
   private Calculator calculator;

   @Test
   @DisplayName("calculate must delegate to calculator")
   void delegate()
   {
      doReturn(ResultType.INTEGER).when(calculator).getResultType();
      doReturn(new BigDecimal(123)).when(calculator).calculate(List.of(1, 2), OperationType.ADD);

      CalculatorService service = new CalculatorService(List.of(calculator));

      Number result = service.calculate(List.of(1, 2), OperationType.ADD, ResultType.INTEGER);

      assertThat(result).isEqualTo(new BigDecimal(123));
      verify(calculator).calculate(List.of(1, 2), OperationType.ADD); // Admittedly a bit nitpick-y, since the result should be specific enough, but better safe than sorry
   }
}