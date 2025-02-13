package io.github.devgrgur.adresarlevak.repository;

import io.github.devgrgur.adresarlevak.api.dto.CreatePersonRequestDTO;
import io.github.devgrgur.adresarlevak.api.dto.UpdateEmailAddressRequestDTO;
import io.github.devgrgur.adresarlevak.api.dto.UpdatePersonRequestDTO;
import io.github.devgrgur.adresarlevak.api.dto.UpdatePhoneNumberRequestDTO;
import io.github.devgrgur.adresarlevak.api.exception.InvalidGenderException;
import io.github.devgrgur.adresarlevak.api.exception.PersonNotFoundException;
import io.github.devgrgur.adresarlevak.api.exception.PinNotUniqueException;
import io.github.devgrgur.adresarlevak.model.EmailAddress;
import io.github.devgrgur.adresarlevak.model.GenderType;
import io.github.devgrgur.adresarlevak.model.Person;
import io.github.devgrgur.adresarlevak.model.PhoneNumber;
import io.github.devgrgur.adresarlevak.repository.mapper.EmailAddressRowMapper;
import io.github.devgrgur.adresarlevak.repository.mapper.PersonRowMapper;
import io.github.devgrgur.adresarlevak.repository.mapper.PhoneNumberRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PersonRepository implements PersonRepositoryInterface {

    private final JdbcTemplate jdbcTemplate;
    private final PersonRowMapper personRowMapper;
    private final PhoneNumberRowMapper phoneNumberRowMapper;
    private final EmailAddressRowMapper emailAddressRowMapper;

    public PersonRepository(
        JdbcTemplate jdbcTemplate,
        PersonRowMapper personRowMapper,
        PhoneNumberRowMapper phoneNumberRowMapper,
        EmailAddressRowMapper emailAddressRowMapper
    ) {
        this.jdbcTemplate = jdbcTemplate;
        this.personRowMapper = personRowMapper;
        this.phoneNumberRowMapper = phoneNumberRowMapper;
        this.emailAddressRowMapper = emailAddressRowMapper;
    }

    @Override
    public void createPerson(CreatePersonRequestDTO personRequestDTO)
    {
        if (!validatePinIsUnique(personRequestDTO.getPin())) {
            throw new PinNotUniqueException("Person's pin must be unique!");
        }

        String createPersonSQL = "INSERT INTO person (first_name, last_name, pin, gender) VALUES (?, ?, ?, ?::gender_type)";

        this.jdbcTemplate.update(
                createPersonSQL,
                personRequestDTO.getFirstName(),
                personRequestDTO.getLastName(),
                personRequestDTO.getPin(),
                personRequestDTO.getGender().toUpperCase()
        );

        String fetchCreatedPersonSQL = "SELECT * FROM person WHERE pin = ?";
        Person createdPerson = this.jdbcTemplate.queryForObject(fetchCreatedPersonSQL, this.personRowMapper, personRequestDTO.getPin());

        String insertPersonPhoneNumbersSQL = "INSERT INTO person_phone_number (person_id, phone_number) VALUES (?, ?)";
        String insertPersonEmailAddressesSQL = "INSERT INTO person_email_address (person_id, email_address) VALUES (?, ?)";

        List<Object[]> phoneNumberBatch = personRequestDTO.getPhoneNumbers()
                .stream()
                .map(phone -> new Object[] { createdPerson.getId(), phone })
                .toList();

        List<Object[]> emailAddressBatch = personRequestDTO.getEmailAddresses()
                .stream()
                .map(phone -> new Object[] { createdPerson.getId(), phone })
                .toList();

        jdbcTemplate.batchUpdate(insertPersonPhoneNumbersSQL, phoneNumberBatch);
        jdbcTemplate.batchUpdate(insertPersonEmailAddressesSQL, emailAddressBatch);
    }

    @Override
    public void deletePersonById(int id)
    {
        if (!doesPersonExist(id)) {
            throw new PersonNotFoundException("Person with ID " + id + " could not be found!");
        }

        String deletePersonSQL = "DELETE FROM person WHERE id = ?";
        String deletePersonPhoneNumbersSQL = "DELETE FROM person_phone_number WHERE person_id = ?";
        String deletePersonEmailAddressesSQL = "DELETE FROM person_email_address WHERE person_id = ?";

        this.jdbcTemplate.update(deletePersonSQL, id);
        this.jdbcTemplate.update(deletePersonPhoneNumbersSQL, id);
        this.jdbcTemplate.update(deletePersonEmailAddressesSQL, id);
    }

    @Override
    public void updatePerson(int id, UpdatePersonRequestDTO personRequestDTO)
    {
        if (!doesPersonExist(id)) {
            throw new PersonNotFoundException("Person with ID " + id + " could not be found!");
        }

        if (!validatePinIsUnique(id, personRequestDTO.getPin())) {
            throw new PinNotUniqueException("Person's pin must be unique!");
        }

        String updatePersonSQL = "UPDATE person SET first_name = ?, last_name = ?, pin = ?, gender = ?::gender_type WHERE id = ?";

        this.jdbcTemplate.update(
                updatePersonSQL,
                personRequestDTO.getFirstName(),
                personRequestDTO.getLastName(),
                personRequestDTO.getPin(),
                personRequestDTO.getGender().toUpperCase(),
                id
        );
    }

    @Override
    public List<Person> queryPeopleByFirstLastNameAndPin(String searchQuery)
    {
        String fetchPeopleBySearchQuerySQL =
                "SELECT * FROM person " +
                "WHERE LOWER(first_name) LIKE LOWER(?) " +
                "OR LOWER(last_name) LIKE LOWER(?) " +
                "OR LOWER(pin) LIKE LOWER(?)";

        String likePattern = "%" + searchQuery + "%";

        List<Person> fetchedPeople = this.jdbcTemplate.query(fetchPeopleBySearchQuerySQL, this.personRowMapper, likePattern, likePattern, likePattern);

        for (Person fetchedPerson : fetchedPeople) {
            fetchedPerson.setPhoneNumbers(fetchPhoneNumbers(fetchedPerson));
            fetchedPerson.setEmailAddresses(fetchEmailAddresses(fetchedPerson));
        }

        return fetchedPeople;
    }

    @Override
    public List<Person> filterPeopleByGender(String gender)
    {
        if (!doesGenderExist(gender)) {
            throw new InvalidGenderException("Gender type " + gender + " does not exist! Only use MALE, FEMALE or OTHER!");
        }

        String filterPeopleByGenderSQL = "SELECT * FROM person WHERE gender = ?::gender_type";

        List<Person> filteredPeople = this.jdbcTemplate.query(filterPeopleByGenderSQL, this.personRowMapper, gender.toUpperCase());

        for (Person filteredPerson : filteredPeople) {
            filteredPerson.setPhoneNumbers(fetchPhoneNumbers(filteredPerson));
            filteredPerson.setEmailAddresses(fetchEmailAddresses(filteredPerson));
        }

        return filteredPeople;
    }

    @Override
    public List<Person> fetchAllPeople()
    {
        String fetchAllPeopleSQL = "SELECT * FROM person";

        List<Person> people = this.jdbcTemplate.query(fetchAllPeopleSQL, this.personRowMapper);

        for (Person person : people) {
            person.setPhoneNumbers(fetchPhoneNumbers(person));
            person.setEmailAddresses(fetchEmailAddresses(person));
        }

        return people;
    }

    @Override
    public void updatePhoneNumber(int id, UpdatePhoneNumberRequestDTO phoneNumberRequestDTO)
    {
        if (!doesPersonExist(id)) {
            throw new PersonNotFoundException("Person with ID " + id + " could not be found!");
        }

        String deleteExistingPhoneNumbersSQL = "DELETE FROM person_phone_number WHERE person_id = ?";
        this.jdbcTemplate.update(deleteExistingPhoneNumbersSQL, id);

        String insertNewPhoneNumbersSQL = "INSERT INTO person_phone_number (person_id, phone_number) VALUES (?, ?)";

        List<Object[]> phoneNumberBatch = phoneNumberRequestDTO.getPhoneNumbers()
                .stream()
                .map(phone -> new Object[] { id, phone })
                .toList();

        jdbcTemplate.batchUpdate(insertNewPhoneNumbersSQL, phoneNumberBatch);
    }

    @Override
    public void updateEmailAddress(int id, UpdateEmailAddressRequestDTO emailAddressRequestDTO)
    {
        if (!doesPersonExist(id)) {
            throw new PersonNotFoundException("Person with ID " + id + " could not be found!");
        }

        String deleteExistingEmailAddressesSQL = "DELETE FROM person_email_address WHERE person_id = ?";
        this.jdbcTemplate.update(deleteExistingEmailAddressesSQL, id);

        String insertNewEmailAddressesSQL = "INSERT INTO person_email_address (person_id, email_address) VALUES (?, ?)";

        List<Object[]> emailAddressBatch = emailAddressRequestDTO.getEmailAddresses()
                .stream()
                .map(phone -> new Object[] { id, phone })
                .toList();

        jdbcTemplate.batchUpdate(insertNewEmailAddressesSQL, emailAddressBatch);
    }

    @Override
    public boolean validatePinIsUnique(int userId, String pin)
    {
        String findPersonWithPinSQL = "SELECT * FROM person WHERE pin = ?";
        List<Person> fetchedData = this.jdbcTemplate.query(findPersonWithPinSQL, this.personRowMapper, pin);

        if (fetchedData.isEmpty()) {
            return true;
        }

        // In case we want to update user's data but keep his PIN the same
        Person fetchedPerson = fetchedData.getFirst();

        return fetchedPerson.getId() == userId && fetchedPerson.getPin().equals(pin);
    }

    @Override
    public boolean validatePinIsUnique(String pin)
    {
        String findPersonWithPinSQL = "SELECT * FROM person WHERE pin = ?";
        List<Person> fetchedData = this.jdbcTemplate.query(findPersonWithPinSQL, this.personRowMapper, pin);

        return fetchedData.isEmpty();
    }

    private List<PhoneNumber> fetchPhoneNumbers(Person person)
    {
        String fetchPhoneNumbersSQL = "SELECT * FROM person_phone_number WHERE person_id = ?";

        return this.jdbcTemplate.query(fetchPhoneNumbersSQL, phoneNumberRowMapper, person.getId());
    }

    private List<EmailAddress> fetchEmailAddresses(Person person)
    {
        String fetchEmailAddressesSQL = "SELECT * FROM person_email_address WHERE person_id = ?";

        return this.jdbcTemplate.query(fetchEmailAddressesSQL, emailAddressRowMapper, person.getId());
    }

    private boolean doesPersonExist(int personId)
    {
        String doesPersonExistSQL = "SELECT * FROM person WHERE id = ?";

        try {
            this.jdbcTemplate.queryForObject(doesPersonExistSQL, this.personRowMapper, personId);
        }catch (Exception exception) {
            return false;
        }

        return true;
    }

    private boolean doesGenderExist(String gender)
    {
        try {
            GenderType.valueOf(gender.toUpperCase());
        }catch (Exception exception) {
            return false;
        }

        return true;
    }
}
