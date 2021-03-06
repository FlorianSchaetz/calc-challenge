package de.irian.challenge.calculator.controller;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import de.irian.challenge.calculator.domain.ErrorResult;

@ControllerAdvice
public class CalculationExceptionHandler
{
   // Exception handling if the calculator itself throws an error
   @ExceptionHandler(value = { ArithmeticException.class, IllegalArgumentException.class })
   protected ResponseEntity<ErrorResult> handleRuntimeException(RuntimeException ex)
   {
      return new ResponseEntity<>(new ErrorResult(ex.getLocalizedMessage()), HttpStatus.BAD_REQUEST);
   }

   // Exception handling for valid json but invalid values (as specified in CalculationRequest)
   @ExceptionHandler(value = MethodArgumentNotValidException.class)
   protected ResponseEntity<ErrorResult> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex)
   {
      // Make the error messages short and snappy
      String errorMsg = ex.getFieldErrors().stream()
            .map(e -> String.format("%s %s", e.getField(), e.getDefaultMessage()))
            .collect(Collectors.joining("; "));

      return new ResponseEntity<>(new ErrorResult(errorMsg), HttpStatus.BAD_REQUEST);
   }

   // Exception handling when the input json is not correct
   @ExceptionHandler(value = HttpMessageNotReadableException.class)
   protected ResponseEntity<ErrorResult> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex)
   {
      // The default error message tends to be a bit verbose and includes implementation details (package names) that we do not want to show to the outside world, so let's clean up the message...
      String msg = ex.getLocalizedMessage();
      int index = msg.indexOf("; nested exception"); // only until here the message actually talks about the json error, rest is nested exception(s)
      if (index > -1)
      {
         String errorMsg = msg.substring(0, index)
               .replace("de.irian.challenge.calculator.domain.", ""); // remove the package names, nobody needs them outside
         return new ResponseEntity<>(new ErrorResult(errorMsg), HttpStatus.BAD_REQUEST);
      }
      return new ResponseEntity<>(new ErrorResult("Cannot parse json message"), HttpStatus.BAD_REQUEST);

   }
}
