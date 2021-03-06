package de.irian.challenge.calculator.service.calculation;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import de.irian.challenge.calculator.domain.ResultType;

// Since we are using Spring autowiring to get the correct calculators, this test ensures that there is no problem concerning the registration of the Calculator beans
@SpringBootTest
class CalculatorBeanIT
{
   @Autowired
   private List<Calculator> calculators;

   @Test
   @DisplayName("All Calculators must return a non-null ResultType on getResultType()")
   void hasResultType()
   {
      final Set<Calculator> calculatorsWithoutResultType = calculators.stream()
            .filter(c -> c.getResultType() == null)
            .collect(Collectors.toSet());

      Assertions.assertThat(calculatorsWithoutResultType).as("Calculators returning null on getResultType()").isEmpty();
   }

   @Test
   @DisplayName("Exactly one Calculator must be defined as a spring bean for each ResultType")
   void wellDefined()
   {
      final List<ResultType> calculatorResultTypes = calculators.stream().map(Calculator::getResultType).collect(Collectors.toList());

      final EnumSet<ResultType> missing = EnumSet.complementOf(EnumSet.copyOf(calculatorResultTypes));

      SoftAssertions softly = new SoftAssertions();

      softly.assertThat(missing).as("ResultTypes with no Calculator defined").isEmpty();

      Set<ResultType> uniques = new HashSet<>();
      Set<ResultType> duplicates = calculatorResultTypes.stream()
            .filter(e -> !uniques.add(e))
            .collect(Collectors.toSet());

      softly.assertThat(duplicates).as("ResultTypes with more than one Calculator defined").isEmpty();

      softly.assertAll();
   }
}