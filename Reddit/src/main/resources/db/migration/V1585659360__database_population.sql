INSERT INTO users (login, email, password, registration_date)
VALUES ('dude', 'mail@mali', 'password', current_date- 3),
       ('me', 'mail@gmai.com', 'qwerty', current_date- 2),
       ('user', 'usermail@mali', '1234', current_date- 2),
       ('admin', 'admm@mali', 'geekhub', current_date- 3),
       ('guy', 'gymail@mali', 'pas1234', current_date- 3);

INSERT INTO subreddits (name, creator_id, creation_date)
VALUES ('AskReddit', 4, current_date- 3),
       ('java', 4, current_date- 3),
       ('AskUbuntu', 4, current_date- 3);

INSERT INTO posts (title, creator_id, subreddit_id, content, creation_date)
VALUES ('Subreddit created', 4, 1, 'Subreddit where you can ask any questions', current_date- 3),
       ('Apache velocity', 5, 1, 'How to use apache velocity?', current_date- 2),
       ('SQL in java ', 5, 2, 'Why shouldn`t i use sql in java file?', current_date- 2),
       ('Microphone ubuntu', 2, 3, 'My mic isn`t working. Google didn`t helped. Any suggestions?', current_date- 2);

INSERT INTO comments (creator_id, post_id, content, creation_date)
VALUES (3, 2, 'Go to r/programming', current_date- 2),
       (5, 2, 'It does not exist yet', current_date- 2);

INSERT INTO votes (voter_id, applied_id, vote, vote_applicable, vote_date)
VALUES (1, 2, TRUE, 'POST', current_date- 1),
       (2, 2, TRUE, 'POST', current_date- 1),
       (3, 2, TRUE, 'POST', current_date- 1),
       (5, 2, TRUE, 'POST', current_date- 1);

INSERT INTO subreddit_user (user_id, subreddit_id)
VALUES (4, 1),
       (1, 1),
       (2, 1),
       (5, 1);
