DROP TABLE IF EXISTS books_genres;
DROP TABLE IF EXISTS genres;
DROP TABLE IF EXISTS comments;
DROP TABLE IF EXISTS books;
DROP TABLE IF EXISTS authors;

CREATE TABLE genres
(
    id   BIGSERIAL NOT NULL PRIMARY KEY,
    name VARCHAR(64)
);

CREATE TABLE authors
(
    id   BIGSERIAL   NOT NULL PRIMARY KEY,
    name VARCHAR(64) NOT NULL
);

CREATE TABLE books
(
    id        BIGSERIAL    NOT NULL PRIMARY KEY,
    title     VARCHAR(128) NOT NULL,
    author_id BIGINT       NOT NULL UNIQUE REFERENCES authors (id)
);

CREATE TABLE books_genres
(
    id       BIGSERIAL NOT NULL PRIMARY KEY,
    book_id  BIGINT    NOT NULL REFERENCES books (id),
    genre_id BIGINT    NOT NULL REFERENCES genres (id),
    UNIQUE (book_id, genre_id)
);

CREATE TABLE comments
(
    id      BIGSERIAL PRIMARY KEY,
    book_id BIGINT REFERENCES BOOKS (id),
    text    TEXT CHECK (length(text) <= 4096) NOT NULL
);
