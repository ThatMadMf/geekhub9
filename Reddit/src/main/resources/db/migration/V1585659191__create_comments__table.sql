CREATE TABLE IF NOT EXISTS comments (
    id serial       PRIMARY KEY,
    creator_id   VARCHAR(64) NOT NULL REFERENCES users(id),
    post_id         INTEGER NOT NULL REFERENCES posts(id),
    content         VARCHAR (512),
    creation_date   TIMESTAMP NOT NULL
);

