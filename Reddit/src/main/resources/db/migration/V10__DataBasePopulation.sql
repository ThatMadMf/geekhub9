INSERT  INTO reddit.users VALUES ('adsdfasdfasdfasdfasd','dude', 'mail@mali', 'password', select now() -3);
INSERT  INTO reddit.users VALUES ('xcvbcxvbxcvbxcvbxxbc','me', 'mail@gmai.com', 'qwerty', select now() -2);
INSERT  INTO reddit.users VALUES ('dasasdfasdfasdfasdff','user', 'usermail@mali', '1234', select now() -2);
INSERT  INTO reddit.users VALUES ('wqewqerqwerqwerqwrqw','admin', 'admm@mali', 'geekhub', select now() -3);
INSERT  INTO reddit.users VALUES ('uytoyutyouytyityotry','guy', 'gymail@mali', 'pas1234', select now() -3);

INSERT  INTO reddit.subreddits (name, creator_login, creation_date)
VALUES ('AskReddit', 'admin', select now() -3);
INSERT  INTO reddit.subreddits (name, creator_login, creation_date)
VALUES ('java', 'admin',select now() -3);
INSERT  INTO reddit.subreddits (name, creator_login, creation_date)
VALUES ('AskUbuntu', 'admin', select now() -3);

INSERT  INTO reddit.posts (title, creator_login, subreddit_id, content, creation_date)
VALUES ('Subreddit created', 'admin', 1, 'Subreddit where you can ask any questions', select now() -3);
INSERT  INTO reddit.posts (title, creator_login, subreddit_id, content, creation_date)
VALUES ('Apache velocity', 'guy', 1, 'How to use apache velocity?', select now() -2);
INSERT  INTO reddit.posts (title, creator_login, subreddit_id, content, creation_date)
VALUES ('SQL in java ', 'guy', 2, 'Why shouldn`t i use sql in java file?', select now() -2);
INSERT  INTO reddit.posts (title, creator_login, subreddit_id, content, creation_date)
VALUES ('Microphone ubuntu', 'me', 3, 'My mic isn`t working. Google didn`t helped. Any suggestions?', select now() -2);

INSERT  INTO reddit.comments (creator_login, post_id, content, creation_date)
VALUES ('user', 2, 'Go to r/programming', select now() -2);
INSERT  INTO reddit.comments (creator_login, post_id, content, creation_date)
VALUES ('guy', 2, 'It does not exist yet', select now() -2);

INSERT  INTO reddit.votes (voter_login, post_id, vote, vote_date)
VALUES ('dude', 2, TRUE, select now() -1);
INSERT  INTO reddit.votes (voter_login, post_id, vote, vote_date)
VALUES ('me', 2, TRUE , select now() -1);
INSERT  INTO reddit.votes (voter_login, post_id, vote, vote_date)
VALUES ('user', 2, TRUE , select now() -1);
INSERT  INTO reddit.votes (voter_login, post_id, vote, vote_date)
VALUES ('guy', 2, TRUE , select now() -1);

INSERT  INTO reddit.subreddit_user (user_login, subreddit_id) VALUES ('admin', 1);
INSERT  INTO reddit.subreddit_user (user_login, subreddit_id) VALUES ('dude', 1);
INSERT  INTO reddit.subreddit_user (user_login, subreddit_id) VALUES ('me', 1);
INSERT  INTO reddit.subreddit_user (user_login, subreddit_id) VALUES ('guy', 1);
INSERT  INTO reddit.subreddit_user (user_login, subreddit_id) VALUES ('user', 1);
