package io.github.devgrgur.adresarlevak.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.devgrgur.adresarlevak.AdresarLevakApplication;
import io.github.devgrgur.adresarlevak.api.controller.dataset.ApiDataset;
import io.github.devgrgur.adresarlevak.api.dto.CreatePersonRequestDTO;
import io.github.devgrgur.adresarlevak.api.dto.UpdatePersonRequestDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = AdresarLevakApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PersonControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreatingPersonWithNonUniquePinCausesBadRequest() throws Exception
    {
        // Creating first person with unique pin
        CreatePersonRequestDTO personOne = ApiDataset.createPersonRequestDTO();
        String jsonPayloadOne = objectMapper.writeValueAsString(personOne);

        mockMvc.perform(post("/api/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPayloadOne))
                .andExpect(status()
                .isOk());

        // Creating second person with the same pin
        CreatePersonRequestDTO personTwo = ApiDataset.createPersonRequestDTO();
        personTwo.setPin(personOne.getPin());
        String jsonPayloadTwo = objectMapper.writeValueAsString(personOne);

        mockMvc.perform(post("/api/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPayloadTwo))
                .andExpect(status()
                .isBadRequest());
    }

    @Test
    void testCreatingPersonWithInvalidPinCausesBadRequest() throws Exception
    {
        // Creating person with invalid pin
        CreatePersonRequestDTO createPersonRequestDTO = ApiDataset.createPersonRequestDTO();
        createPersonRequestDTO.setPin("123456789123456789");

        String jsonPayload = objectMapper.writeValueAsString(createPersonRequestDTO);

        mockMvc.perform(post("/api/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPayload)).andExpect(status()
                .isBadRequest());
    }

    @Test
    void testCreatingPersonWithInvalidPhoneNumberCausesBadRequest() throws Exception
    {
        // Creating person with one invalid phone number
        CreatePersonRequestDTO createPersonRequestDTO = ApiDataset.createPersonRequestDTO();
        createPersonRequestDTO.setPhoneNumbers(List.of("0912345678", "0912345678255215"));

        String jsonPayload = objectMapper.writeValueAsString(createPersonRequestDTO);

        mockMvc.perform(post("/api/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPayload)).andExpect(status()
                .isBadRequest());
    }

    @Test
    void testCreatingPersonWithInvalidEmailAddressCausesBadRequest() throws Exception
    {
        // Creating person with one invalid email address
        CreatePersonRequestDTO createPersonRequestDTO = ApiDataset.createPersonRequestDTO();
        createPersonRequestDTO.setEmailAddresses(List.of("invalidEmailAddress", "test@example.com"));

        String jsonPayload = objectMapper.writeValueAsString(createPersonRequestDTO);

        mockMvc.perform(post("/api/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPayload)).andExpect(status()
                .isBadRequest());
    }

    @Test
    void testUpdatingPersonThatDoesNotExistCausesNotFound() throws Exception
    {
        UpdatePersonRequestDTO updatePersonRequestDTO = ApiDataset.createUpdatePersonRequestDTO();
        String jsonPayload = objectMapper.writeValueAsString(updatePersonRequestDTO);

        int fakePersonId = 123;

        mockMvc.perform(put("/api/person/{id}", fakePersonId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPayload)).andExpect(status()
                .isNotFound());
    }

    @Test
    void testUpdatingPersonWithPinThatIsNotUniqueCausesBadRequest() throws Exception
    {
        // Creating 2 people with different pins
        CreatePersonRequestDTO personOne = ApiDataset.createPersonRequestDTO();
        String jsonPayloadOne = objectMapper.writeValueAsString(personOne);

        String personTwoPin = "98765432198";
        CreatePersonRequestDTO personTwo = ApiDataset.createPersonRequestDTO();
        personTwo.setPin(personTwoPin);
        String jsonPayloadTwo = objectMapper.writeValueAsString(personTwo);

        mockMvc.perform(post("/api/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPayloadOne));

        mockMvc.perform(post("/api/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPayloadTwo));

        // Creating a update request where the first person tries to set the same pin
        // as the second person

        UpdatePersonRequestDTO personOneUpdateRequest = ApiDataset.createUpdatePersonRequestDTO();
        personOneUpdateRequest.setPin(personTwoPin);

        String jsonPayload = objectMapper.writeValueAsString(personOneUpdateRequest);

        int personOneId = 1;

        mockMvc.perform(put("/api/person/{id}", personOneId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPayload)).andExpect(status()
                .isBadRequest());
    }

    @Test
    void testDeletingPersonThatDoesNotExistCausesNotFound() throws Exception
    {
        int fakePersonId = 123;

        mockMvc.perform(delete("/api/person/{id}", fakePersonId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status()
                .isNotFound());
    }

}
