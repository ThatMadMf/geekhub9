CREATE TABLE IF NOT EXISTS users (
    id                  SERIAL PRIMARY KEY,
    username            VARCHAR(30) UNIQUE NOT NULL,
    password            VARCHAR(256),
    password_uses       INTEGER,
    invalid_inputs      INTEGER
)
