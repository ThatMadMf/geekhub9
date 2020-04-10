CREATE TABLE IF NOT EXISTS subreddit_user (
    user_id         INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    subreddit_id    INTEGER REFERENCES subreddits(id),
    CONSTRAINT      subreddit_user_pkey PRIMARY KEY (user_id, subreddit_id)
);
