package io.github.devgrgur.adresarlevak.api.exception.handler;

import io.github.devgrgur.adresarlevak.api.exception.InvalidGenderException;
import io.github.devgrgur.adresarlevak.api.exception.PersonNotFoundException;
import io.github.devgrgur.adresarlevak.api.exception.PinNotUniqueException;
import io.github.devgrgur.adresarlevak.api.exception.dto.ValidationExceptionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationExceptionDTO> handleValidationExceptions(MethodArgumentNotValidException exception)
    {
        Map<String, String> errors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        error -> error.getField(),
                        error -> error.getDefaultMessage(),
                        (existing, replacement) -> existing // if there are duplicate keys, keep the existing one
                ));

        ValidationExceptionDTO validationExceptionDTO = new ValidationExceptionDTO(
                HttpStatus.BAD_REQUEST.value(),
                "Request field validation failed!",
                errors
        );

        return new ResponseEntity<>(validationExceptionDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PersonNotFoundException.class)
    public ResponseEntity<String> handlePersonNotFoundException(PersonNotFoundException ex)
    {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(PinNotUniqueException.class)
    public ResponseEntity<String> handlePinNotUniqueException(PinNotUniqueException ex)
    {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(InvalidGenderException.class)
    public ResponseEntity<?> handlerGenderNotValidException(InvalidGenderException ex)
    {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

}
