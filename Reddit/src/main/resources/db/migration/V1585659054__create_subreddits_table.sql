CREATE TABLE IF NOT EXISTS subreddits (
    id              SERIAL PRIMARY KEY,
    name            VARCHAR(30) UNIQUE NOT NULL,
    creator_id      VARCHAR(64) NOT NULL REFERENCES users(id),
    creation_date   TIMESTAMP NOT NULL
);