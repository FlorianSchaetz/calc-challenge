package de.irian.challenge.calculator.domain;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import de.irian.challenge.calculator.controller.validation.NoNullElements;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CalculationRequest
{
   @NotNull
   @NotEmpty
   @NoNullElements
   // Please note: This leads to a loss of precision since jackson will parse it to double, should either be BigDecimal from the start or a better json solution (subtypes are a good idea)
   private final List<Number> values;
   @NotNull
   private final OperationType operation;
   @NotNull
   private final ResultType type;

}
