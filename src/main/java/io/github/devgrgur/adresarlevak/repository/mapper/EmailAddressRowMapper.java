package io.github.devgrgur.adresarlevak.repository.mapper;

import io.github.devgrgur.adresarlevak.model.EmailAddress;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class EmailAddressRowMapper implements RowMapper<EmailAddress> {

    private final String COLUMN_ID = "id";
    private final String COLUMN_PERSON_ID = "person_id";
    private final String COLUMN_EMAIL_ADDRESS = "email_address";

    @Override
    public EmailAddress mapRow(ResultSet resultSet, int rowNum) throws SQLException
    {
        return new EmailAddress(
                resultSet.getInt(COLUMN_ID),
                resultSet.getInt(COLUMN_PERSON_ID),
                resultSet.getString(COLUMN_EMAIL_ADDRESS)
        );
    }
}
