CREATE TABLE IF NOT EXISTS posts (
    id              SERIAL PRIMARY KEY,
    title           VARCHAR(30) NOT NULL,
    creator_id      INTEGER NOT NULL REFERENCES users(id),
    subreddit_id    INTEGER REFERENCES subreddits(id),
    content         VARCHAR (512),
    creation_date   TIMESTAMP NOT NULL
);