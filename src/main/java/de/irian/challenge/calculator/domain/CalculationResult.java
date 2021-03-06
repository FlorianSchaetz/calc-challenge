package de.irian.challenge.calculator.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * An instance of this class will be returned to the user in case the calculation was successful
 */
@AllArgsConstructor
@Getter
public class CalculationResult
{
   private final Number result;
}
