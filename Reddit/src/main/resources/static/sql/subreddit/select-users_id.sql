SELECT u.* FROM users AS u
INNER JOIN subreddit_user AS su ON u.id = su.user_id
WHERE su.subreddit_id = ?