SELECT s.* FROM subreddits AS s
INNER JOIN subreddit_user AS su ON s.id = su.subreddit_id
WHERE su.user_id = ?