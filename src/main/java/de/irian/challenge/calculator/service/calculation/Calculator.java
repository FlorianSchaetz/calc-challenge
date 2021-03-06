package de.irian.challenge.calculator.service.calculation;

import java.util.List;

import de.irian.challenge.calculator.domain.OperationType;
import de.irian.challenge.calculator.domain.ResultType;

/**
 * The base interface for all calculators
 * <p>
 * To correctly implement a new calculator, please be aware of the following rules:
 * <p>
 * 1) Every Calculator must return a non-null ResultType via getResultType()
 * 2) Exactly ONE Calculator bean must exist in the ApplicationContext for each ResultType
 */
public interface Calculator
{
   /**
    * @return The ResultType this calculator will process
    */
   ResultType getResultType();

   /**
    * This method must be implemented to do the actual calculation, including conversion of values to the target type
    *
    * @param values        The numeric values (can be Double or Integer)
    * @param operationType The type of the operation to do on the values
    * @return The result, type is calculator-specific but has to be json-serializable in this ApplicationContext
    */
   Number calculate(List<Number> values, OperationType operationType);
}
