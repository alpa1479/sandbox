-- @formatter:off
insert into authors(name)
values ('Thomas Artis'),
       ('Rania Martinez');

insert into books(title, author_id)
values ('The Oblong Box', (select id from authors where name = 'Thomas Artis')),
       ('Skin Deep',      (select id from authors where name = 'Rania Martinez'));

insert into genres(name)
values ('drama'),
       ('thriller'),
       ('detective');

insert into books_genres(book_id, genre_id)
values ((select id from books where title = 'The Oblong Box'), (select id from genres where name = 'drama')),
       ((select id from books where title = 'The Oblong Box'), (select id from genres where name = 'thriller')),
       ((select id from books where title = 'Skin Deep'),      (select id from genres where name = 'detective'));

insert into comments(book_id, text)
values ((select id from books where title = 'The Oblong Box'), 'Cras non velit nec nisi vulputate nonummy. Maecenas tincidunt lacus at velit. Vivamus vel nulla eget eros elementum pellentesque.'),
       ((select id from books where title = 'The Oblong Box'), 'Suspendisse potenti. In eleifend quam a odio. In hac habitasse platea dictumst.'),
       ((select id from books where title = 'Skin Deep'),      'Nullam sit amet turpis elementum ligula vehicula consequat. Morbi a ipsum. Integer a nibh');
-- @formatter:on