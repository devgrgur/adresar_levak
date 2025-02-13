package io.github.devgrgur.adresarlevak.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePersonRequestDTO {

    @NotEmpty
    @Schema(description = "First name of the person", example = "John")
    private String firstName;

    @NotEmpty
    @Schema(description = "Last name of the person", example = "Doe")
    private String lastName;

    @NotEmpty
    @Pattern(regexp = "^\\d{11}$", message = "Must be a numeric string of exactly 11 digits!")
    @Schema(description = "Pin of the person", example = "12345678910")
    private String pin;

    @Pattern(regexp = "(?i)^(MALE|FEMALE|OTHER)$", message = "Must must be either Male, Female or Other!")
    @Schema(description = "Gender of the person", example = "MALE")
    private String gender;

}
