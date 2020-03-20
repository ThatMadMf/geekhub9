CREATE TABLE IF NOT EXISTS reddit.posts (
    id              SERIAL PRIMARY KEY,
    title           VARCHAR(30) UNIQUE NOT NULL,
    creator_login   VARCHAR(64) NOT NULL REFERENCES reddit.users(login),
    subreddit_id    INTEGER REFERENCES reddit.subreddits(id),
    content         VARCHAR (512),
    creation_date   TIMESTAMP NOT NULL
);
