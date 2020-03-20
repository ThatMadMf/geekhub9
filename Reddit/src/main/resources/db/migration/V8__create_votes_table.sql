CREATE TABLE IF NOT EXISTS reddit.votes (
    id              SERIAL PRIMARY KEY,
    voter_login     VARCHAR(64) NOT NULL REFERENCES reddit.users(login),
    post_id         INTEGER REFERENCES reddit.posts(id),
    comment_id      INTEGER REFERENCES reddit.comments(id),
    vote            BOOLEAN,
    vote_date       TIMESTAMP NOT NULL
);