CREATE TABLE IF NOT EXISTS reddit.votes (
    id                  SERIAL PRIMARY KEY,
    voter_login         VARCHAR(64) NOT NULL REFERENCES reddit.users(login),
    applied_id          INTEGER,
    vote                BOOLEAN,
    vote_date           TIMESTAMP NOT NULL,
    vote_applicable     VARCHAR(10) NOT NULL
);