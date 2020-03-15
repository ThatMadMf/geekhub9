create table if not exists reddit.subreddits (
    id serial primary key,
    name varchar(30) unique not null,
    creator_login varchar(64) not null references reddit.users(login),
    creation_date timestamp not null
);