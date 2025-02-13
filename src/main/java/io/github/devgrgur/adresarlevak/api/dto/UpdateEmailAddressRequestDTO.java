package io.github.devgrgur.adresarlevak.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UpdateEmailAddressRequestDTO {

    @Schema(description = "Email addresses of the person", example = "[\"example@example.com\", \"example2@example.com\"]")
    private List<
            @NotEmpty
            @Email(message = "Must be a valid email address!")
                    String> emailAddresses = new ArrayList<>();

}
