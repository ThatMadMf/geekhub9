create table if not exists reddit.users (
    id serial primary key,
    login varchar(30) not null,
    email varchar(30),
    password varchar(256),
    link varchar(256),
    registration_date timestamp not null
);