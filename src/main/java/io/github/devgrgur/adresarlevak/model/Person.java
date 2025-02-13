package io.github.devgrgur.adresarlevak.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Person {

    private int id;

    private String firstName;

    private String lastName;

    private String pin;

    private GenderType gender;

    private List<PhoneNumber> phoneNumbers;

    private List<EmailAddress> emailAddresses;
}
