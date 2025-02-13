package io.github.devgrgur.adresarlevak.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import io.github.devgrgur.adresarlevak.api.controller.dataset.ApiDataset;
import io.github.devgrgur.adresarlevak.api.dto.CreatePersonRequestDTO;
import io.github.devgrgur.adresarlevak.api.dto.UpdateEmailAddressRequestDTO;
import io.github.devgrgur.adresarlevak.api.dto.UpdatePersonRequestDTO;
import io.github.devgrgur.adresarlevak.api.dto.UpdatePhoneNumberRequestDTO;
import io.github.devgrgur.adresarlevak.service.PersonService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class PersonControllerUnitTest {

    @Mock
    private PersonService personServiceMock;

    @InjectMocks
    private PersonController personControllerMock;

    @Test
    void testPersonIsSuccessfullyCreated()
    {
        // Arrange
        CreatePersonRequestDTO createPersonRequestDTO = ApiDataset.createPersonRequestDTO();

        // Act
        ResponseEntity<String> responseEntity = personControllerMock.createPerson(createPersonRequestDTO);

        // Assert
        verify(personServiceMock, times(1)).createPerson(createPersonRequestDTO);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testPersonIsSuccessfullyDeleted()
    {
        // Arrange
        int personId = 123;

        // Act
        ResponseEntity<String> responseEntity = personControllerMock.deletePerson(personId);

        // Assert
        verify(personServiceMock, times(1)).deletePersonById(personId);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testPersonIsSuccessfullyUpdated()
    {
        // Arrange
        int personId = 123;
        UpdatePersonRequestDTO updatePersonRequestDTO = ApiDataset.createUpdatePersonRequestDTO();

        // Act
        ResponseEntity<String> responseEntity = personControllerMock.updatePerson(personId, updatePersonRequestDTO);

        // Assert
        verify(personServiceMock, times(1)).updatePerson(personId, updatePersonRequestDTO);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testFetchingAllPeopleIsSuccessful()
    {
        // Act
        ResponseEntity<?> responseEntity = personControllerMock.fetchPeople(null);

        // Assert
        verify(personServiceMock, times(1)).fetchAllPeople();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testFetchingPeopleByGenderIsSuccessful()
    {
        // Arrange
        String gender = "male";

        // Act
        ResponseEntity<?> responseEntity = personControllerMock.fetchPeople(gender);

        // Assert
        verify(personServiceMock, times(1)).filterPeopleByGender(gender);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testFetchingPeopleByFirstLastNameAndPinIsSuccessful()
    {
        // Arrange
        String searchQuery = "John";

        // Act
        ResponseEntity<?> responseEntity = personControllerMock.queryPeopleByFirstLastNameAndPin(searchQuery);

        // Assert
        verify(personServiceMock, times(1)).queryPeopleByFirstLastNameAndPin(searchQuery);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testUpdatingPersonPhoneNumberIsSuccessful()
    {
        // Arrange
        int personId = 123;
        UpdatePhoneNumberRequestDTO updatePhoneNumberRequestDTO = ApiDataset.createUpdatePhoneNumberRequestDTO();

        // Act
        ResponseEntity<?> responseEntity = personControllerMock.updatePhoneNumber(personId, updatePhoneNumberRequestDTO);

        // Assert
        verify(personServiceMock, times(1)).updatePhoneNumber(personId, updatePhoneNumberRequestDTO);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testUpdatingPersonEmailAddressIsSuccessful()
    {
        // Arrange
        int personId = 123;
        UpdateEmailAddressRequestDTO updateEmailAddressRequestDTO = ApiDataset.createUpdateEmailAddressRequestDTO();

        // Act
        ResponseEntity<?> responseEntity = personControllerMock.updateEmailAddress(personId, updateEmailAddressRequestDTO);

        // Assert
        verify(personServiceMock, times(1)).updateEmailAddress(personId, updateEmailAddressRequestDTO);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}
