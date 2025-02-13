package io.github.devgrgur.adresarlevak.api.exception.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class ValidationExceptionDTO {

    private int statusCode;
    private String errorMessage;
    private Map<String, String> validationErrors;

    public ValidationExceptionDTO(int statusCode, String errorMessage, Map<String, String> validationErrors) {
        this.statusCode = statusCode;
        this.errorMessage = errorMessage;
        this.validationErrors = validationErrors;
    }

}
