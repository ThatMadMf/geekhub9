CREATE TABLE IF NOT EXISTS reddit.subreddits (
    id              SERIAL PRIMARY KEY,
    name            VARCHAR(30) UNIQUE NOT NULL,
    creator_login   VARCHAR(64) NOT NULL REFERENCES reddit.users(login),
    creation_date   TIMESTAMP NOT NULL
);