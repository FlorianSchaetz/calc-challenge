package de.irian.challenge.calculator.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * An instance of this class will be returned to the user in case of an error
 */
@AllArgsConstructor
@Getter
public class ErrorResult
{
   private final String error;
}
