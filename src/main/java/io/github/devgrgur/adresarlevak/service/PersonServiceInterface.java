package io.github.devgrgur.adresarlevak.service;

import io.github.devgrgur.adresarlevak.api.dto.CreatePersonRequestDTO;
import io.github.devgrgur.adresarlevak.api.dto.UpdateEmailAddressRequestDTO;
import io.github.devgrgur.adresarlevak.api.dto.UpdatePersonRequestDTO;
import io.github.devgrgur.adresarlevak.api.dto.UpdatePhoneNumberRequestDTO;
import io.github.devgrgur.adresarlevak.model.Person;

import java.util.List;

public interface PersonServiceInterface {

    /**
     * Create a new person
     */
    void createPerson(CreatePersonRequestDTO createPersonRequestDTO);

    /**
     * Update a person's existing data
     */
    void updatePerson(int id, UpdatePersonRequestDTO personRequestDTO);

    /**
     * Delete an existing person by id
     */
    void deletePersonById(int id);

    /**
     * Return a list of people that match the search query
     */
    List<Person> queryPeopleByFirstLastNameAndPin(String searchQuery);

    /**
     * Return a filtered list of people by their genders
     */
    List<Person> filterPeopleByGender(String gender);

    /**
     * Fetch all people
     */
    List<Person> fetchAllPeople();

    /**
     * Validate that a person's PIN is unique when updating data
     */
    boolean validatePinIsUnique(int personId, String pin);

    /**
     * Validate that a person's PIN is unique when creating data
     */
    boolean validatePinIsUnique(String pin);

    /**
     * Update a person's phone numbers
     */
    void updatePhoneNumber(int id, UpdatePhoneNumberRequestDTO phoneNumberRequestDTO);

    /**
     * Update a person's phone numbers
     */
    void updateEmailAddress(int id, UpdateEmailAddressRequestDTO phoneNumberRequestDTO);
}
