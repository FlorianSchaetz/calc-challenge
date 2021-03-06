package de.irian.challenge.calculator.service.calculation;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.stereotype.Component;

import de.irian.challenge.calculator.domain.OperationType;
import de.irian.challenge.calculator.domain.ResultType;

@Component
public class SafeCalculator extends AbstractCalculator<BigDecimal>
{
   // Number of digits right of the "."
   private static final int DEFAULT_SCALE = 124;

   @Override
   public ResultType getResultType()
   {
      return ResultType.SAFE;
   }

   @Override
   public BigDecimal calculate(BigDecimal left, BigDecimal right, OperationType operationType)
   {
      switch (operationType)
      {
         case ADD:
            return left.add(right).stripTrailingZeros();
         case SUB:
            return left.subtract(right).stripTrailingZeros();
         case DIV:
            if (right.compareTo(BigDecimal.ZERO) == 0)
            {
               throw new ArithmeticException(String.format("Divide by zero (%s / %s)", left, right));
            }
            return left.divide(right, DEFAULT_SCALE, RoundingMode.HALF_UP).stripTrailingZeros();
         case MUL:
            return left.multiply(right).stripTrailingZeros();
         default:
            throw new IllegalStateException(String.format("Unknown OperationType '%s'", operationType));
      }
   }

   @Override
   public BigDecimal convert(Number number)
   {
      return new BigDecimal(number.toString());
   }
}
