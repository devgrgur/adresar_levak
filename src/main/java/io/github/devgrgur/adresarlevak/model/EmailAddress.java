package io.github.devgrgur.adresarlevak.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailAddress {

    private int id;
    private int personId;
    private String emailAddress;

    public EmailAddress(int id, int personId, String emailAddress){
        this.id = id;
        this.personId = personId;
        this.emailAddress = emailAddress;
    }

}
