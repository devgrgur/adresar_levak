package io.github.devgrgur.adresarlevak.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PhoneNumber {

    private int id;
    private int personId;
    private String phoneNumber;

    public PhoneNumber(int id, int personId, String phoneNumber){
        this.id = id;
        this.personId = personId;
        this.phoneNumber = phoneNumber;
    }
}
