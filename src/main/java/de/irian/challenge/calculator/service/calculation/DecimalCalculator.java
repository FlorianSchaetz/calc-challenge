package de.irian.challenge.calculator.service.calculation;

import org.springframework.stereotype.Controller;

import de.irian.challenge.calculator.domain.OperationType;
import de.irian.challenge.calculator.domain.ResultType;

@Controller
public class DecimalCalculator extends AbstractCalculator<Double>
{
   @Override
   public ResultType getResultType()
   {
      return ResultType.DECIMAL;
   }

   @Override
   public Double calculate(Double left, Double right, OperationType operationType)
   {
      switch (operationType)
      {
         case ADD:
            return left + right;
         case SUB:
            return left - right;
         case DIV:
            if (right.compareTo(0.0) == 0)
            {
               throw new ArithmeticException(String.format("Divide by zero (%s / %s)", left, right));
            }
            return left / right;
         case MUL:
            return left * right;
         default:
            throw new IllegalStateException(String.format("Unknown OperationType '%s'", operationType));
      }
   }

   @Override
   public Double convert(Number n)
   {
      return n.doubleValue();
   }
}
