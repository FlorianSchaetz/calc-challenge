package de.irian.challenge.calculator.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import de.irian.challenge.calculator.domain.CalculationRequest;
import de.irian.challenge.calculator.domain.CalculationResult;
import de.irian.challenge.calculator.service.CalculatorService;

@RestController
public class CalculatorController
{
   private final CalculatorService calculatorService;

   @Autowired
   public CalculatorController(final CalculatorService calculatorService)
   {
      this.calculatorService = calculatorService;
   }

   @PostMapping("/calculate")
   public CalculationResult calculate(@Valid @RequestBody CalculationRequest calculationRequest)
   {
      Number number = calculatorService.calculate(calculationRequest.getValues(), calculationRequest.getOperation(), calculationRequest.getType());
      return new CalculationResult(number);
   }
}
