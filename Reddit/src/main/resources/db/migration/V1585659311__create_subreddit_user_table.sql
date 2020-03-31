CREATE TABLE IF NOT EXISTS subreddit_user (
    user_id         VARCHAR(64) NOT NULL REFERENCES users(id),
    subreddit_id    INTEGER REFERENCES subreddits(id),
    CONSTRAINT      subreddit_user_pkey PRIMARY KEY (user_id, subreddit_id)
);
