package io.github.devgrgur.adresarlevak.api.controller;

import io.github.devgrgur.adresarlevak.api.dto.CreatePersonRequestDTO;
import io.github.devgrgur.adresarlevak.api.dto.UpdateEmailAddressRequestDTO;
import io.github.devgrgur.adresarlevak.api.dto.UpdatePersonRequestDTO;
import io.github.devgrgur.adresarlevak.api.dto.UpdatePhoneNumberRequestDTO;
import io.github.devgrgur.adresarlevak.model.Person;
import io.github.devgrgur.adresarlevak.service.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/person")
@Tag(name = "Person Management", description = "Operations related to users.")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService)
    {
        this.personService = personService;
    }

    @Operation(summary = "Create a new person")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Person was successfully created."),
            @ApiResponse(responseCode = "400", description = "The pin is not unique or other data is incorrect."),
            @ApiResponse(responseCode = "500", description = "Error while trying to create a new person."),
    })
    @PostMapping
    public ResponseEntity<String> createPerson(@Valid @RequestBody CreatePersonRequestDTO createPersonRequestDTO)
    {
        this.personService.createPerson(createPersonRequestDTO);

        return ResponseEntity.ok("Person has been created.");
    }

    @Operation(summary = "Delete a person by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Person has been deleted if it exists."),
            @ApiResponse(responseCode = "404", description = "Person could not be found."),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePerson(@PathVariable("id") int id)
    {
        this.personService.deletePersonById(id);

        return ResponseEntity.ok("Person's data has been deleted.");
    }

    @Operation(summary = "Update a existing person")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Person data has been updated if it exists."),
            @ApiResponse(responseCode = "400", description = "The pin is not unique."),
            @ApiResponse(responseCode = "404", description = "Person could not be found."),
    })
    @PutMapping("/{id}")
    public ResponseEntity<String> updatePerson(@PathVariable("id") int id, @Valid @RequestBody UpdatePersonRequestDTO personRequestDTO)
    {
        this.personService.updatePerson(id, personRequestDTO);

        return ResponseEntity.ok("Person's data has been updated.");
    }

    @Operation(summary = "Fetch people according to searchQuery")
    @GetMapping("/search")
    public ResponseEntity<List<Person>> queryPeopleByFirstLastNameAndPin(@RequestParam("searchQuery") String searchQuery)
    {
        return ResponseEntity.ok(this.personService.queryPeopleByFirstLastNameAndPin(searchQuery));
    }

    @Operation(summary = "Fetch all people, or people with specified gender")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All people that exist in storage have been fetched."),
            @ApiResponse(responseCode = "400", description = "Specified gender does not exist."),
    })
    @GetMapping
    public ResponseEntity<?> fetchPeople(@RequestParam(value = "gender", required = false) String gender)
    {
        if (gender != null) {
            return ResponseEntity.ok(this.personService.filterPeopleByGender(gender));
        }

        return ResponseEntity.ok(this.personService.fetchAllPeople());
    }

    @Operation(summary = "Update a person's phone numbers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Person's phone numbers have been updated."),
            @ApiResponse(responseCode = "404", description = "Person could not be found."),
    })
    @PutMapping("/phonenumber/{id}")
    public ResponseEntity<String> updatePhoneNumber(@PathVariable("id") int id, @Valid @RequestBody UpdatePhoneNumberRequestDTO phoneNumberRequestDTO)
    {
        this.personService.updatePhoneNumber(id, phoneNumberRequestDTO);

        return ResponseEntity.ok("Person's phone numbers were updated.");
    }

    @Operation(summary = "Update a person's email addresses")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Person's email addresses have been updated."),
            @ApiResponse(responseCode = "404", description = "Person could not be found."),
    })
    @PutMapping("/emailaddress/{id}")
    public ResponseEntity<String> updateEmailAddress(@PathVariable("id") int id, @Valid @RequestBody UpdateEmailAddressRequestDTO emailAddressRequestDTO)
    {
        this.personService.updateEmailAddress(id, emailAddressRequestDTO);

        return ResponseEntity.ok("Person's email addresses were updated.");
    }
}
