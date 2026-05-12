create table users
(
    id       bigint auto_increment
        primary key,
    name     varchar(255) not null,
    emal     varchar(255) not null,
    password varchar(255) not null
);

create table addresses
(
    id      bigint auto_increment
        primary key,
    street  varchar(255) not null,
    city    varchar(255) not null,
    zip     varchar(255) not null,
    user_id bigint       not null,
    constraint addresses_users_id_fk
        foreign key (user_id) references users (id)
);

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

create table tags
(
    id   bigint auto_increment
        primary key,
    name varchar(255) null
);
create table user_tags
(
    user_id bigint not null,
    tag_id  bigint not null,
    constraint user_tags_pk
        primary key (user_id, tag_id),
    constraint user_tags_tags_id_fk
        foreign key (tag_id) references tags (id)
            on delete cascade,
    constraint user_tags_users_id_fk
        foreign key (user_id) references users (id)
            on delete cascade
);

alter table users
    change emal email varchar(255) not null;

alter table addresses
    add state varchar(255) null;

create table categories
(
    id   tinyint auto_increment
        primary key,
    name varchar(255) not null
);
create table products
(
    id          bigint auto_increment
        primary key,
    name        varchar(255)   not null,
    price       decimal(10, 2) null,
    category_id tinyint        not null,
    constraint products_categories_id_fk
        foreign key (category_id) references categories (id)
);

ALTER TABLE products
    ADD `description` TEXT NULL;

ALTER TABLE products
    MODIFY `description` TEXT NOT NULL;

CREATE TABLE wishlist
(
    product_id BIGINT NOT NULL,
    user_id    BIGINT NOT NULL
);

ALTER TABLE wishlist
    ADD CONSTRAINT fk_wishlist_on_product FOREIGN KEY (product_id) REFERENCES products (id);

ALTER TABLE wishlist
    ADD CONSTRAINT fk_wishlist_on_user FOREIGN KEY (user_id) REFERENCES users (id);

create table users_seq
(
    next_val bigint null
);
INSERT INTO users_seq VALUES (1);

create table addresses_seq
(
    next_val bigint null
);
INSERT INTO addresses_seq VALUES (1);


DELIMITER $$

CREATE PROCEDURE findProductsByPrice(
    minPrice DECIMAL(10,2),
    maxPrice DECIMAL(10,2)
)

BEGIN
SELECT id,name,description,price,category_id
FROM products
WHERE price BETWEEN minPrice AND maxPrice
ORDER BY name;
END $$

DELIMITER ;