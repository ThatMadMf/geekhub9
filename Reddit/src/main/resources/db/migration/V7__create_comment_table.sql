create table if not exists reddit.comments (
    id serial primary key,
    creator_login varchar(64) not null references reddit.users(login),
    post_id integer not null references reddit.posts(id),
    content varchar (512),
    creation_date timestamp not null
);
