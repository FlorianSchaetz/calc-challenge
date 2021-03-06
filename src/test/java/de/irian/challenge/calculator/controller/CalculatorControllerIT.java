package de.irian.challenge.calculator.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import de.irian.challenge.calculator.domain.CalculationRequest;
import de.irian.challenge.calculator.domain.OperationType;
import de.irian.challenge.calculator.domain.ResultType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class CalculatorControllerIT
{
   private static final String CONTEXT_PATH = "/calculate";

   @Nested
   @DisplayName("Tests using CalculationResult as input to verify correctness of controller")
   class BasicTests
   {
      @Autowired
      private TestRestTemplate restTemplate;

      @Test
      @DisplayName("Adding of three integers must return correct result in result type INTEGER")
      void integer_add()
      {
         CalculationRequest calculationRequest = new CalculationRequest(List.of(1, 2, 3), OperationType.ADD, ResultType.SAFE);
         final ResponseEntity<String> result = restTemplate.postForEntity(CONTEXT_PATH, calculationRequest, String.class);

         assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
         assertThat(result.getBody()).isEqualTo("{\"result\":6}");
      }

      @Test
      @DisplayName("Adding of one integer and two doubles must return correct result on result type DECIMAL")
      void double_add()
      {
         CalculationRequest calculationRequest = new CalculationRequest(List.of(1, 2.2, 3.3), OperationType.ADD, ResultType.DECIMAL);
         final ResponseEntity<String> result = restTemplate.postForEntity(CONTEXT_PATH, calculationRequest, String.class);

         assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
         assertThat(result.getBody()).isEqualTo("{\"result\":6.5}");
      }

      @Test
      @DisplayName("Division by zero for doubles must return correct error")
      void double_divide_by_zero()
      {
         CalculationRequest calculationRequest = new CalculationRequest(List.of(1.1, 2.2, 0.0), OperationType.DIV, ResultType.SAFE);

         final ResponseEntity<String> result = restTemplate.postForEntity(CONTEXT_PATH, calculationRequest, String.class);

         assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
         assertThat(result.getBody()).isEqualTo("{\"error\":\"Divide by zero (0.5 / 0.0)\"}");
      }

      @Test
      @DisplayName("Operations for result type integer with double must return correct error")
      void double_in_integer_calculation()
      {
         CalculationRequest calculationRequest = new CalculationRequest(List.of(1, 2, 3.3), OperationType.DIV, ResultType.INTEGER);

         final ResponseEntity<String> result = restTemplate.postForEntity(CONTEXT_PATH, calculationRequest, String.class);

         assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
         assertThat(result.getBody()).isEqualTo("{\"error\":\"Using a DECIMAL value (3.3) for an INTEGER calculation is not allowed.\"}");
      }

      @Test
      @DisplayName("Must return correct error result for empty values")
      void valuesEmpty()
      {
         CalculationRequest calculationRequest = new CalculationRequest(List.of(), OperationType.DIV, ResultType.INTEGER);

         final ResponseEntity<String> result = restTemplate.postForEntity(CONTEXT_PATH, calculationRequest, String.class);

         assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
         assertThat(result.getBody()).isEqualTo("{\"error\":\"values must not be empty\"}");
      }

      @Test
      @DisplayName("Must return correct error result for null values")
      void valuesNull()
      {
         CalculationRequest calculationRequest = new CalculationRequest(null, OperationType.DIV, ResultType.INTEGER);

         final ResponseEntity<String> result = restTemplate.postForEntity(CONTEXT_PATH, calculationRequest, String.class);

         assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
         assertThat(result.getBody()).contains("values must not be empty"); // The ordering of those two is basically random
         assertThat(result.getBody()).contains("values must not be null");
         assertThat(result.getBody()).startsWith("{\"error\":\"");
      }

      @Test
      @DisplayName("Must return correct error result for null operationType")
      void operationTypeNull()
      {
         CalculationRequest calculationRequest = new CalculationRequest(List.of(1, 2), null, ResultType.INTEGER);

         final ResponseEntity<String> result = restTemplate.postForEntity(CONTEXT_PATH, calculationRequest, String.class);

         assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
         assertThat(result.getBody()).isEqualTo("{\"error\":\"operation must not be null\"}");
      }

      @Test
      @DisplayName("Must return correct error result for null resultType")
      void resultTypeNull()
      {
         CalculationRequest calculationRequest = new CalculationRequest(List.of(1, 2), OperationType.ADD, null);

         final ResponseEntity<String> result = restTemplate.postForEntity(CONTEXT_PATH, calculationRequest, String.class);

         assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
         assertThat(result.getBody()).isEqualTo("{\"error\":\"type must not be null\"}");
      }

      @Test
      @DisplayName("Must return correct error result for nulls in values list")
      void valuesContainingNull()
      {
         CalculationRequest calculationRequest = new CalculationRequest(Collections.singletonList(null), OperationType.ADD, ResultType.INTEGER);

         final ResponseEntity<String> result = restTemplate.postForEntity(CONTEXT_PATH, calculationRequest, String.class);

         assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
         assertThat(result.getBody()).isEqualTo("{\"error\":\"values must not contain null elements\"}");
      }

      @Test
      @DisplayName("Must return correct error result all null parameters")
      void allNull()
      {
         CalculationRequest calculationRequest = new CalculationRequest(null, null, null);

         final ResponseEntity<String> result = restTemplate.postForEntity(CONTEXT_PATH, calculationRequest, String.class);

         assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
         assertThat(result.getBody()).startsWith("{\"error\":\"");
         assertThat(result.getBody()).contains("values must not be empty");
         assertThat(result.getBody()).contains("values must not be null");
         assertThat(result.getBody()).contains("type must not be null");
         assertThat(result.getBody()).contains("operation must not be null");
      }
   }

   @Nested
   @DisplayName("Tests based on raw json input to verify format and parsing")
   class RawTests
   {
      @Autowired
      private MockMvc mockMvc;

      @Test
      @DisplayName("Must accept and return correct json")
      void must_accept_and_return_correct_format() throws Exception
      {
         mockMvc.perform(
               post(CONTEXT_PATH)
                     .content("{ \"values\" : [1,2,3], \"operation\" : \"sub\", \"type\" : \"integer\"}")
                     .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().is(200))
               .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.content().string("{\"result\":-4}"));
      }

      @Test
      @DisplayName("Must return correct error json for calculator error")
      void must_accept_and_return_correct_format_for_error() throws Exception
      {
         mockMvc.perform(
               post(CONTEXT_PATH)
                     .content("{ \"values\" : [1,2,3.0], \"operation\" : \"sub\", \"type\" : \"integer\"}")
                     .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().is(400))
               .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.content().string("{\"error\":\"Using a DECIMAL value (3.0) for an INTEGER calculation is not allowed.\"}"));
      }

      @Test
      @DisplayName("Must return correct error if values are not numbers but strings")
      void must_accept_and_return_correct_format_for_values_not_numbers_error() throws Exception
      {
         mockMvc.perform(
               post(CONTEXT_PATH)
                     .content("{ \"values\" : [\"a\", \"b\"], \"operation\" : \"sub\", \"type\" : \"integer\"}")
                     .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().is(400))
               .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.content().string("{\"error\":\"JSON parse error: Cannot deserialize value of type `java.lang.Number` from String \\\"a\\\": not a valid number\"}"));
      }

      @Test
      @DisplayName("Must return correct error if operationType is illegal")
      void must_accept_and_return_correct_format_for_illegal_operationType_error() throws Exception
      {
         mockMvc.perform(
               post(CONTEXT_PATH)
                     .content("{ \"values\" : [1], \"operation\" : \"pow\", \"type\" : \"integer\"}")
                     .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().is(400))
               .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.content().string(
                     "{\"error\":\"JSON parse error: Cannot deserialize value of type `OperationType` from String \\\"pow\\\": not one of the values accepted for Enum class: [sub, div, mul, add]\"}"));
      }

      @Test
      @DisplayName("Must return correct error if resultType is illegal")
      void must_accept_and_return_correct_format_for_illegal_resultType_error() throws Exception
      {
         mockMvc.perform(
               post(CONTEXT_PATH)
                     .content("{ \"values\" : [1], \"operation\" : \"add\", \"type\" : \"fraction\"}")
                     .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().is(400))
               .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.content().string(
                     "{\"error\":\"JSON parse error: Cannot deserialize value of type `ResultType` from String \\\"fraction\\\": not one of the values accepted for Enum class: [decimal, safe, integer]\"}"));
      }

      @Test
      @DisplayName("Must return correct error if message is completely empty")
      void must_accept_and_return_correct_format_for_non_json() throws Exception
      {
         mockMvc.perform(
               post(CONTEXT_PATH)
                     .content(" <html><body>I am an HTML page</body></html>")
                     .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().is(400))
               .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.content().string(
                     "{\"error\":\"JSON parse error: Unexpected character ('<' (code 60)): expected a valid value (JSON String, Number, Array, Object or token 'null', 'true' or 'false')\"}"));
      }

      @Test
      @DisplayName("Must return correct error if message is not even json")
      void must_accept_and_return_correct_format_for_empty_body() throws Exception
      {
         mockMvc.perform(
               post(CONTEXT_PATH)
                     .content("")
                     .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().is(400))
               .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.content().string(
                     "{\"error\":\"Cannot parse json message\"}"));
      }
   }

}