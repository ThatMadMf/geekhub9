CREATE SCHEMA IF NOT EXISTS geekhub;
create table if not exists geekhub.history (
    id serial primary key,
    operation varchar(30) not null,
    codec varchar(30),
    user_input varchar(256),
    operation_date timestamp not null
);