create table profiles
(
    id             bigint auto_increment
        primary key,
    bio            text                   null,
    phone_number   varchar(15)            null,
    date_of_birth  DATE                   null,
    loyalty_points INT UNSIGNED default 0 null,
    constraint profiles_users_id_fk
        foreign key (id) references users (id)
);