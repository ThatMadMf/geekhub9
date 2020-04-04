CREATE TABLE IF NOT EXISTS votes (
    id                  SERIAL PRIMARY KEY,
    voter_id            INTEGER NOT NULL REFERENCES users(id),
    applied_id          INTEGER,
    vote                BOOLEAN,
    vote_date           TIMESTAMP NOT NULL,
    vote_applicable     VARCHAR(10) NOT NULL
);
