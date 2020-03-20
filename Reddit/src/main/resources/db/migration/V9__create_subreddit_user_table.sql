CREATE TABLE IF NOT EXISTS reddit.subreddit_user (
    user_login         VARCHAR(64) NOT NULL REFERENCES reddit.users(login),
    subreddit_id    INTEGER REFERENCES reddit.subreddits(id),
    CONSTRAINT      subreddit_user_pkey PRIMARY KEY (user_login, subreddit_id)
);