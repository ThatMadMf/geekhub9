INSERT INTO reddit.users
VALUES ('adsdfasdfasdfasdfasd', 'dude', 'mail@mali', 'password', current_date- 3),
       ('xcvbcxvbxcvbxcvbxxbc', 'me', 'mail@gmai.com', 'qwerty', current_date- 2),
       ('dasasdfasdfasdfasdff', 'user', 'usermail@mali', '1234', current_date- 2),
       ('wqewqerqwerqwerqwrqw', 'admin', 'admm@mali', 'geekhub', current_date- 3),
       ('uytoyutyouytyityotry', 'guy', 'gymail@mali', 'pas1234', current_date- 3);

INSERT INTO reddit.subreddits (name, creator_login, creation_date)
VALUES ('AskReddit', 'admin', current_date- 3),
       ('java', 'admin', current_date- 3),
       ('AskUbuntu', 'admin', current_date- 3);

INSERT INTO reddit.posts (title, creator_login, subreddit_id, content, creation_date)
VALUES ('Subreddit created', 'admin', 1, 'Subreddit where you can ask any questions', current_date- 3),
       ('Apache velocity', 'guy', 1, 'How to use apache velocity?', current_date- 2),
       ('SQL in java ', 'guy', 2, 'Why shouldn`t i use sql in java file?', current_date- 2),
       ('Microphone ubuntu', 'me', 3, 'My mic isn`t working. Google didn`t helped. Any suggestions?', current_date- 2);

INSERT INTO reddit.comments (creator_login, post_id, content, creation_date)
VALUES ('user', 2, 'Go to r/programming', current_date- 2),
       ('guy', 2, 'It does not exist yet', current_date- 2);

INSERT INTO reddit.votes (voter_login, applied_id, vote, vote_applicable, vote_date)
VALUES ('dude', 2, TRUE, 'POST', current_date- 1),
       ('me', 2, TRUE, 'POST', current_date- 1),
       ('user', 2, TRUE, 'POST', current_date- 1),
       ('guy', 2, TRUE, 'POST', current_date- 1);

INSERT INTO reddit.subreddit_user (user_login, subreddit_id)
VALUES ('admin', 1),
       ('dude', 1),
       ('me', 1),
       ('guy', 1);
