create table if not exists reddit.votes (
    id serial primary key,
    voter_login varchar(64) not null references reddit.users(login),
    post_id integer references reddit.posts(id),
    comment_id integer references reddit.comments(id),
    vote boolean,
    vote_date timestamp not null
);