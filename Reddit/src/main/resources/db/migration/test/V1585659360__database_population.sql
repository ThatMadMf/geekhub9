INSERT INTO users (login, email, password, registration_date)
VALUES ('dude', 'mail@mali', '$2y$12$0xtZnhhNZZw5HxRTE/R8/.cSRM404qEARMT3VFv9rnkD.5z6k1nZW', current_date- 3),
       ('me', 'mail@gmai.com', '$2y$12$8eBPB0SlSRh.cxjks96QdOi0jMWlXo3PgvU0dDuHYWsjkvwhhr1DW', current_date- 2),
       ('user', 'usermail@mali', '$2y$12$mt0Tuxdr2Qy4N8G8S2WUMuagcRIUTwmWTUFig2xXA5GmvlWECbP4u', current_date- 2),
       ('admin', 'admm@mali', '$2y$12$pRcmCsdZgwfKQUkaFiNnLuFILC48BaRVOp8tRZ9MoM1kgL9i5bt3G', current_date- 3),
       ('guy', 'gymail@mali', '$2y$12$ihOc1iJVf34WcOC8ace7CO85kznOJZqdCGt18YwkDNrNFbzkRTyTa', current_date- 3);

INSERT INTO subreddits (name, creator_id, creation_date)
VALUES ('AskReddit', 4, current_date - 3),
       ('java', 4, current_date - 3),
       ('AskUbuntu', 4, current_date - 3),
       ('Test', 4, current_date - 4);

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

INSERT INTO roles (role)
VALUES  ('SUPER_ADMIN'),
        ('ADMIN'),
        ('USER');

INSERT INTO role_user (user_id, role_id)
VALUES  (1, 3),
        (2, 3),
        (3, 3),
        (4, 1),
        (5, 3);

INSERT INTO subreddit_user (user_id, subreddit_id)
VALUES (4, 1),
       (1, 1),
       (2, 1),
       (5, 1);
