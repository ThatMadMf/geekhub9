create table if not exists reddit.posts (
    id serial primary key,
    title varchar(30) unique not null,
    creator_login varchar(64) not null references reddit.users(login),
    subreddit_id integer references reddit.subreddits(id),
    content varchar (512),
    creation_date timestamp not null
);
