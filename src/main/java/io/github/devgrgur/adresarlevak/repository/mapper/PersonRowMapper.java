package io.github.devgrgur.adresarlevak.repository.mapper;

import io.github.devgrgur.adresarlevak.model.GenderType;
import io.github.devgrgur.adresarlevak.model.Person;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class PersonRowMapper implements RowMapper<Person> {

    private final String COLUMN_ID = "id";
    private final String COLUMN_FIRST_NAME = "first_name";
    private final String COLUMN_LAST_NAME = "last_name";
    private final String COLUMN_PIN = "pin";
    private final String COLUMN_GENDER = "gender";

    public Person mapRow(ResultSet resultSet, int rowNum) throws SQLException
    {
        Person person = new Person();
        person.setId(resultSet.getInt(COLUMN_ID));
        person.setFirstName(resultSet.getString(COLUMN_FIRST_NAME));
        person.setLastName(resultSet.getString(COLUMN_LAST_NAME));
        person.setPin(resultSet.getString(COLUMN_PIN));

        String genderString = resultSet.getString(COLUMN_GENDER);
        person.setGender(GenderType.valueOf(genderString.toUpperCase()));

        return person;
    }
}
