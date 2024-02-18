INSERT INTO genres(name)
VALUES ('drama'),
       ('thriller'),
       ('detective'),
       ('sci-fi'),
       ('adventure'),
       ('fantasy');

INSERT INTO authors(name)
VALUES ('Thomas Artis'),
       ('Rania Martinez'),
       ('Nathanial Snugg'),
       ('Kathryn Armatys'),
       ('Row Le Sarr'),
       ('Krystal Ryves'),
       ('Pat Cambridge');

INSERT INTO books(title, author_id)
VALUES ('The Oblong Box', (select id from authors where name = 'Thomas Artis')),
       ('Skin Deep', (select id from authors where name = 'Rania Martinez')),
       ('Summer Catch', (select id from authors where name = 'Nathanial Snugg')),
       ('The Missionaries', (select id from authors where name = 'Kathryn Armatys')),
       ('9 Star Hotel (Malon 9 Kochavim)', (select id from authors where name = 'Row Le Sarr')),
       ('Street Kings', (select id from authors where name = 'Krystal Ryves')),
       ('Squeeze', (select id from authors where name = 'Pat Cambridge'));

INSERT INTO books_genres(book_id, genre_id)
VALUES ((select id from books where title = 'The Oblong Box'), (select id from genres where name = 'drama')),
       ((select id from books where title = 'The Oblong Box'), (select id from genres where name = 'thriller')),
       ((select id from books where title = 'The Oblong Box'), (select id from genres where name = 'detective')),
       ((select id from books where title = 'Skin Deep'), (select id from genres where name = 'thriller')),
       ((select id from books where title = 'Skin Deep'), (select id from genres where name = 'drama')),
       ((select id from books where title = 'Summer Catch'), (select id from genres where name = 'detective')),
       ((select id from books where title = 'Summer Catch'), (select id from genres where name = 'drama')),
       ((select id from books where title = 'The Missionaries'), (select id from genres where name = 'sci-fi')),
       ((select id from books where title = '9 Star Hotel (Malon 9 Kochavim)'),
        (select id from genres where name = 'adventure')),
       ((select id from books where title = 'Street Kings'), (select id from genres where name = 'fantasy')),
       ((select id from books where title = 'Squeeze'), (select id from genres where name = 'thriller'));

INSERT INTO comments(book_id, text)
VALUES ((SELECT id FROM books WHERE title = 'The Oblong Box'),
        'Cras non velit nec nisi vulputate nonummy. Maecenas tincidunt lacus at velit. Vivamus vel nulla eget eros elementum pellentesque.'),
       ((SELECT id FROM books WHERE title = 'The Oblong Box'),
        'Suspendisse potenti. In eleifend quam a odio. In hac habitasse platea dictumst.'),
       ((SELECT id FROM books WHERE title = 'The Oblong Box'),
        'Fusce posuere felis sed lacus. Morbi sem mauris, laoreet ut, rhoncus aliquet, pulvinar sed, nisl. Nunc rhoncus dui vel sem.'),
       ((SELECT id FROM books WHERE title = 'Skin Deep'),
        'Nullam sit amet turpis elementum ligula vehicula consequat. Morbi a ipsum. Integer a nibh'),
       ((SELECT id FROM books WHERE title = 'Skin Deep'),
        'Morbi non lectus. Aliquam sit amet diam in magna bibendum imperdiet. Nullam orci pede, venenatis non, sodales sed, tincidunt eu, felis'),
       ((SELECT id FROM books WHERE title = 'Summer Catch'),
        'Quisque porta volutpat erat. Quisque erat eros, viverra eget, congue eget, semper rutrum, nulla. Nunc purus.'),
       ((SELECT id FROM books WHERE title = 'Summer Catch'),
        'Maecenas leo odio, condimentum id, luctus nec, molestie sed, justo. Pellentesque viverra pede ac diam. Cras pellentesque volutpat dui.'),
       ((SELECT id FROM books WHERE title = 'The Missionaries'),
        'Duis aliquam convallis nunc. Proin at turpis a pede posuere nonummy. Integer non velit.'),
       ((SELECT id FROM books WHERE title = 'The Missionaries'),
        'Donec diam neque, vestibulum eget, vulputate ut, ultrices vel, augue. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae'),
       ((SELECT id FROM books WHERE title = '9 Star Hotel (Malon 9 Kochavim)'),
        'Duis bibendum. Morbi non quam nec dui luctus rutrum. Nulla tellus.'),
       ((SELECT id FROM books WHERE title = '9 Star Hotel (Malon 9 Kochavim)'),
        'Vestibulum quam sapien, varius ut, blandit non, interdum in, ante. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Duis faucibus accumsan odio. Curabitur convallis.'),
       ((SELECT id FROM books WHERE title = 'Street Kings'),
        'Maecenas tristique, est et tempus semper, est quam pharetra magna, ac consequat metus sapien ut nunc. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae'),
       ((SELECT id FROM books WHERE title = 'Squeeze'),
        'Nam ultrices, libero non mattis pulvinar, nulla pede ullamcorper augue, a suscipit nulla elit ac nulla. Sed vel enim sit amet nunc viverra dapibus. Nulla suscipit ligula in lacus.');