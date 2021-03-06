# Java Discord Challenge #2: Bootiful Calculator

Challenge from the folks at https://javadiscord.net/ - see https://github.com/Shaunwild97/CodingChallenges/tree/master/bootiful-calculator

by Flo

## Assumptions

* ResultType "safe" only requires the result of the operation to be free of floating-point errors, the final result may be rounded (needed for calculations like 1.0/3.0, since BigDecimal cannot store
  values like this exactly)

## Remarks

* While Fractions (from Apache commons-math) would lead to even more exact results than BigDecimal, unfortunately 32 / 5 is not a valid json number, so for using it, a change in the result json format
  would be needed - or it would have to be rounded to double in the end, but since the scale of BigDecimal was chosen arbitrarily high, there would be no difference in precision then. Would be easy to
  implement, though.

## Usage

### Build / Install

mvn clean install

### PITest

PITest (Mutation Testing, report will be in target/pit-reports) can be triggered via...

mvn clean test org.pitest:pitest-maven:mutationCoverage

### Running

mvn spring-boot:run

