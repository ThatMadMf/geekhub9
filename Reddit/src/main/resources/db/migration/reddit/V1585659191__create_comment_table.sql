CREATE TABLE IF NOT EXISTS comments (
    id serial       PRIMARY KEY,
    creator_id      INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    post_id         INTEGER NOT NULL REFERENCES posts(id) ON DELETE CASCADE,
    content         VARCHAR (512),
    creation_date   TIMESTAMP NOT NULL
);

