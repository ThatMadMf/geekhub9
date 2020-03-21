INSERT INTO reddit.users
VALUES ('adsdfasdfasdfasdfasd', 'dude', 'mail@mali', 'password', select now() - 3),
       ('xcvbcxvbxcvbxcvbxxbc', 'me', 'mail@gmai.com', 'qwerty', select now() - 2),
       ('dasasdfasdfasdfasdff', 'user', 'usermail@mali', '1234', select now() - 2),
       ('wqewqerqwerqwerqwrqw', 'admin', 'admm@mali', 'geekhub', select now() - 3),
       ('uytoyutyouytyityotry', 'guy', 'gymail@mali', 'pas1234', select now() - 3);

INSERT INTO reddit.subreddits (name, creator_login, creation_date)
VALUES ('AskReddit', 'admin', select now() - 3),
       ('java', 'admin', select now() - 3),
       ('AskUbuntu', 'admin', select now() - 3);

INSERT INTO reddit.posts (title, creator_login, subreddit_id, content, creation_date)
VALUES ('Subreddit created', 'admin', 1, 'Subreddit where you can ask any questions', select now() - 3),
       ('Apache velocity', 'guy', 1, 'How to use apache velocity?', select now() - 2),
       ('SQL in java ', 'guy', 2, 'Why shouldn`t i use sql in java file?', select now() - 2),
       ('Microphone ubuntu', 'me', 3, 'My mic isn`t working. Google didn`t helped. Any suggestions?', select now() - 2);

INSERT INTO reddit.comments (creator_login, post_id, content, creation_date)
VALUES ('user', 2, 'Go to r/programming', select now() - 2),
       ('guy', 2, 'It does not exist yet', select now() - 2);

INSERT INTO reddit.votes (voter_login, applied_id, vote, vote_applicable, vote_date)
VALUES ('dude', 2, TRUE, 'POST', select now() - 1),
       ('me', 2, TRUE, 'POST', select now() - 1),
       ('user', 2, TRUE, 'POST', select now() - 1),
       ('guy', 2, TRUE, 'POST', select now() - 1);

INSERT INTO reddit.subreddit_user (user_login, subreddit_id)
VALUES ('admin', 1),
       ('dude', 1),
       ('me', 1),
       ('guy', 1);
