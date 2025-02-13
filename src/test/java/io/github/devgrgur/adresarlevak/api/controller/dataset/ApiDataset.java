package io.github.devgrgur.adresarlevak.api.controller.dataset;

import io.github.devgrgur.adresarlevak.api.dto.CreatePersonRequestDTO;
import io.github.devgrgur.adresarlevak.api.dto.UpdateEmailAddressRequestDTO;
import io.github.devgrgur.adresarlevak.api.dto.UpdatePersonRequestDTO;
import io.github.devgrgur.adresarlevak.api.dto.UpdatePhoneNumberRequestDTO;

import java.util.ArrayList;
import java.util.List;

public class ApiDataset {

    public static UpdatePhoneNumberRequestDTO createUpdatePhoneNumberRequestDTO()
    {
        UpdatePhoneNumberRequestDTO updatePhoneNumberRequestDTO = new UpdatePhoneNumberRequestDTO();
        updatePhoneNumberRequestDTO.setPhoneNumbers(List.of("0911231234", "0911234321"));

        return updatePhoneNumberRequestDTO;
    }

    public static UpdateEmailAddressRequestDTO createUpdateEmailAddressRequestDTO()
    {
        UpdateEmailAddressRequestDTO updateEmailAddressRequestDTO = new UpdateEmailAddressRequestDTO();
        updateEmailAddressRequestDTO.setEmailAddresses(List.of("test@example.com", "test2@exampl.com"));

        return updateEmailAddressRequestDTO;
    }

    public static CreatePersonRequestDTO createPersonRequestDTO()
    {
        CreatePersonRequestDTO createPersonRequestDTO = new CreatePersonRequestDTO();
        createPersonRequestDTO.setFirstName("John");
        createPersonRequestDTO.setLastName("Doe");
        createPersonRequestDTO.setPin("12345678910");
        createPersonRequestDTO.setGender("Male");

        List<String> phoneNumbers = new ArrayList<>();
        phoneNumbers.add("0911231234");
        phoneNumbers.add("0911234321");
        createPersonRequestDTO.setPhoneNumbers(phoneNumbers);

        List<String> emailAddresses = new ArrayList<>();
        emailAddresses.add("test@example.com");
        emailAddresses.add("test2@exampl.com");
        createPersonRequestDTO.setEmailAddresses(emailAddresses);

        return createPersonRequestDTO;
    }

    public static UpdatePersonRequestDTO createUpdatePersonRequestDTO()
    {
        UpdatePersonRequestDTO updatePersonRequestDTO = new UpdatePersonRequestDTO();
        updatePersonRequestDTO.setFirstName("John");
        updatePersonRequestDTO.setLastName("Doe");
        updatePersonRequestDTO.setPin("12345678910");
        updatePersonRequestDTO.setGender("Male");

        return updatePersonRequestDTO;
    }

}
