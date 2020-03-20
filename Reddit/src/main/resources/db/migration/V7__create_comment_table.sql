CREATE TABLE IF NOT EXISTS reddit.comments (
    id serial       PRIMARY KEY,
    creator_login   VARCHAR(64) NOT NULL REFERENCES reddit.users(login),
    post_id         INTEGER NOT NULL REFERENCES reddit.posts(id),
    content         VARCHAR (512),
    creation_date   TIMESTAMP NOT NULL
);
