package de.irian.challenge.calculator.service.calculation;

import org.springframework.stereotype.Controller;

import de.irian.challenge.calculator.domain.OperationType;
import de.irian.challenge.calculator.domain.ResultType;

@Controller
public class IntegerCalculator extends AbstractCalculator<Integer>
{
   @Override
   public ResultType getResultType()
   {
      return ResultType.INTEGER;
   }

   public Integer calculate(Integer left, Integer right, OperationType operationType)
   {
      switch (operationType)
      {
         case ADD:
            return left + right;
         case SUB:
            return left - right;
         case DIV:
            if (right == 0)
            {
               throw new ArithmeticException(String.format("Divide by zero (%s / %s)", left, right));
            }
            return left / right;
         case MUL:
            return left * right;
         default:
            throw new IllegalStateException(String.format("Unknown OperationType '%s'", operationType)); // Unfortunately not very testable
      }
   }

   @Override
   public Integer convert(Number number)
   {
      if (number instanceof Double)
      {
         throw new IllegalArgumentException(String.format("Using a DECIMAL value (%s) for an INTEGER calculation is not allowed.", number));
      }
      return number.intValue();
   }
}
