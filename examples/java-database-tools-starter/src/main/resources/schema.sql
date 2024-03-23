drop table if exists books_genres cascade;
drop table if exists comments cascade;
drop table if exists authors cascade;
drop table if exists genres cascade;
drop table if exists books cascade;

create table genres
(
    id   bigserial not null primary key,
    name varchar(64)
);

create table authors
(
    id   bigserial   not null primary key,
    name varchar(64) not null
);

create table books
(
    id        bigserial    not null primary key,
    title     varchar(128) not null,
    author_id bigint       not null unique references authors (id)
);

create table books_genres
(
    id       bigserial not null primary key,
    book_id  bigint    not null references books (id),
    genre_id bigint    not null references genres (id),
    unique (book_id, genre_id)
);

create table comments
(
    id      bigserial primary key,
    book_id bigint                            not null references books (id),
    text    text check (length(text) <= 4096) not null
);
