package io.github.devgrgur.adresarlevak.repository.mapper;

import io.github.devgrgur.adresarlevak.model.PhoneNumber;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class PhoneNumberRowMapper implements RowMapper<PhoneNumber> {

    private final String COLUMN_ID = "id";
    private final String COLUMN_PERSON_ID = "person_id";
    private final String COLUMN_PHONE_NUMBER = "phone_number";

    @Override
    public PhoneNumber mapRow(ResultSet resultSet, int rowNum) throws SQLException
    {
        return new PhoneNumber(
                resultSet.getInt(COLUMN_ID),
                resultSet.getInt(COLUMN_PERSON_ID),
                resultSet.getString(COLUMN_PHONE_NUMBER)
        );
    }
}
