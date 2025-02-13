package io.github.devgrgur.adresarlevak.service;

import io.github.devgrgur.adresarlevak.api.dto.CreatePersonRequestDTO;
import io.github.devgrgur.adresarlevak.api.dto.UpdateEmailAddressRequestDTO;
import io.github.devgrgur.adresarlevak.api.dto.UpdatePersonRequestDTO;
import io.github.devgrgur.adresarlevak.api.dto.UpdatePhoneNumberRequestDTO;
import io.github.devgrgur.adresarlevak.model.Person;
import io.github.devgrgur.adresarlevak.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService implements PersonServiceInterface {

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository)
    {
        this.personRepository = personRepository;
    }

    @Override
    public void createPerson(CreatePersonRequestDTO createPersonRequestDTO)
    {
        this.personRepository.createPerson(createPersonRequestDTO);
    }

    @Override
    public void updatePerson(int id, UpdatePersonRequestDTO personRequestDTO)
    {
        this.personRepository.updatePerson(id, personRequestDTO);
    }

    @Override
    public void deletePersonById(int id)
    {
        this.personRepository.deletePersonById(id);
    }

    @Override
    public List<Person> queryPeopleByFirstLastNameAndPin(String searchQuery)
    {
        return this.personRepository.queryPeopleByFirstLastNameAndPin(searchQuery);
    }

    @Override
    public List<Person> filterPeopleByGender(String gender)
    {
        return this.personRepository.filterPeopleByGender(gender);
    }

    @Override
    public List<Person> fetchAllPeople()
    {
        return this.personRepository.fetchAllPeople();
    }

    @Override
    public boolean validatePinIsUnique(int personId, String pin)
    {
        return this.personRepository.validatePinIsUnique(personId, pin);
    }

    @Override
    public boolean validatePinIsUnique(String pin)
    {
        return this.personRepository.validatePinIsUnique(pin);
    }

    @Override
    public void updatePhoneNumber(int id, UpdatePhoneNumberRequestDTO phoneNumberRequestDTO)
    {
        this.personRepository.updatePhoneNumber(id, phoneNumberRequestDTO);
    }

    @Override
    public void updateEmailAddress(int id, UpdateEmailAddressRequestDTO emailAddressRequestDTO)
    {
        this.personRepository.updateEmailAddress(id, emailAddressRequestDTO);
    }
}
