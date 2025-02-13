CREATE TYPE gender_type AS ENUM ('MALE', 'FEMALE', 'OTHER');

CREATE TABLE IF NOT EXISTS person
(
    id         SERIAL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name  VARCHAR(255) NOT NULL,
    pin        VARCHAR(11) NOT NULL UNIQUE,
    gender     gender_type NOT NULL
);

CREATE TABLE IF NOT EXISTS person_phone_number
(
    id           SERIAL PRIMARY KEY,
    person_id    INTEGER NOT NULL,
    phone_number VARCHAR(10),
    FOREIGN KEY (person_id) REFERENCES person(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS person_email_address
(
    id            SERIAL PRIMARY KEY,
    person_id     INTEGER NOT NULL,
    email_address VARCHAR(255),
    FOREIGN KEY (person_id) REFERENCES person(id) ON DELETE CASCADE
);