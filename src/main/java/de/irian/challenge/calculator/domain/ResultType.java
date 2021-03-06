package de.irian.challenge.calculator.domain;

import com.fasterxml.jackson.annotation.JsonValue;

// It would also have been a possibility to set the calculator class/instance here, but I like the loose coupling via Spring a bit better
public enum ResultType
{
   INTEGER, DECIMAL, SAFE;

   // Because the spec demands "integer", "decimal", etc. and enum values are constants and thus always upper-case
   @JsonValue
   public String getJsonName()
   {
      return name().toLowerCase();
   }
}
