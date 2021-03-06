package de.irian.challenge.calculator.service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.irian.challenge.calculator.domain.OperationType;
import de.irian.challenge.calculator.domain.ResultType;
import de.irian.challenge.calculator.service.calculation.Calculator;

@Service
public class CalculatorService
{
   // We could use a various of patterns here, for example a factory, but I like the simple approach that Spring allows (with a good test added for completeness)
   private final Map<ResultType, Calculator> calculators;

   @Autowired
   public CalculatorService(final List<Calculator> calculators)
   {
      this.calculators = calculators.stream().collect(Collectors.toMap(Calculator::getResultType, Function.identity()));
   }

   public Number calculate(List<Number> values, OperationType operationType, ResultType resultType)
   {
      return getCalculator(resultType).calculate(values, operationType);
   }

   private Calculator getCalculator(ResultType resultType)
   {
      return calculators.get(resultType);
   }
}
