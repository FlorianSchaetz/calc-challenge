package de.irian.challenge.calculator.domain;

import com.fasterxml.jackson.annotation.JsonValue;

public enum OperationType
{
   ADD, SUB, MUL, DIV;

   // Because the spec demands "add", "sub", etc. and enum values are constants and thus always upper-case
   @JsonValue
   public String getJsonName()
   {
      return name().toLowerCase();
   }
}
