create table if not exists reddit.users (
    id varchar(64) primary key,
    login varchar(30) unique not null,
    email varchar(30) unique,
    password varchar(256),
    registration_date timestamp not null
);