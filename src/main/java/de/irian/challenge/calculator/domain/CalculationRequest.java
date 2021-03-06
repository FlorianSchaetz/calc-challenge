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
   private final List<Number> values;
   @NotNull
   private final OperationType operation;
   @NotNull
   private final ResultType type;

}
