package io.github.devgrgur.adresarlevak.repository;

import io.github.devgrgur.adresarlevak.api.dto.CreatePersonRequestDTO;
import io.github.devgrgur.adresarlevak.api.dto.UpdateEmailAddressRequestDTO;
import io.github.devgrgur.adresarlevak.api.dto.UpdatePersonRequestDTO;
import io.github.devgrgur.adresarlevak.api.dto.UpdatePhoneNumberRequestDTO;
import io.github.devgrgur.adresarlevak.model.Person;

import java.util.List;

public interface PersonRepositoryInterface {

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
     * Return a filtered list of people by their genders
     */
    List<Person> filterPeopleByGender(String gender);

    /**
     * Validate that a person's PIN is unique when updating data
     */
    boolean validatePinIsUnique(int personId, String pin);

    /**
     * Validate that a person's PIN is unique when creating data
     */
    boolean validatePinIsUnique(String pin);

    /**
     * Return a list of people that match the search query
     */
    List<Person> queryPeopleByFirstLastNameAndPin(String searchQuery);

    /**
     * Fetch data of all people
     */
    List<Person> fetchAllPeople();

    /**
     * Update a person's phone numbers
     */
    void updatePhoneNumber(int id, UpdatePhoneNumberRequestDTO phoneNumberRequestDTO);

    /**
     * Update a person's email addresses
     */
    void updateEmailAddress(int id, UpdateEmailAddressRequestDTO emailAddressRequestDTO);
}
