package io.github.devgrgur.adresarlevak.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UpdatePhoneNumberRequestDTO {

    @Schema(description = "Phone numbers of the person", example = "[\"0911234321\", \"0921234321\"]")
    private List<
            @NotEmpty
            @Pattern(regexp = "^\\d{10}$", message = "Must be a numeric string of exactly 10 digits!")
                    String> phoneNumbers = new ArrayList<>();

}
