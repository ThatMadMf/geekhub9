CREATE TABLE IF NOT EXISTS users (
    id                  SERIAL PRIMARY KEY,
    login               VARCHAR(30) UNIQUE NOT NULL,
    email               VARCHAR(30) UNIQUE,
    password            VARCHAR(256),
    registration_date   TIMESTAMP NOT NULL
)
