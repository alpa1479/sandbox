package edu.sandbox.javadatabasetools.jooq.service;

import edu.sandbox.javadatabasetools.jooq.generated.tables.daos.AuthorDao;
import edu.sandbox.javadatabasetools.jooq.generated.tables.daos.BookDao;
import edu.sandbox.javadatabasetools.jooq.generated.tables.daos.BookGenreDao;
import edu.sandbox.javadatabasetools.jooq.generated.tables.daos.CommentDao;
import edu.sandbox.javadatabasetools.jooq.generated.tables.daos.GenreDao;
import edu.sandbox.javadatabasetools.jooq.generated.tables.pojos.Author;
import edu.sandbox.javadatabasetools.jooq.generated.tables.pojos.Book;
import edu.sandbox.javadatabasetools.jooq.generated.tables.pojos.BookGenre;
import edu.sandbox.javadatabasetools.jooq.generated.tables.pojos.Genre;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static edu.sandbox.javadatabasetools.jooq.generated.tables.Author.AUTHOR;
import static edu.sandbox.javadatabasetools.jooq.generated.tables.Book.BOOK;
import static edu.sandbox.javadatabasetools.jooq.generated.tables.BookGenre.BOOK_GENRE;
import static edu.sandbox.javadatabasetools.jooq.generated.tables.Comment.COMMENT;
import static edu.sandbox.javadatabasetools.jooq.generated.tables.Genre.GENRE;

@Service
@RequiredArgsConstructor
public class BookService {

    private final DSLContext dsl;
    private final BookDao booksDao;
    private final GenreDao genresDao;
    private final AuthorDao authorsDao;
    private final CommentDao commentsDao;
    private final BookGenreDao booksGenresDao;

    public List<Book> findAll() {
        var books = dsl.select(BOOK.ID, BOOK.TITLE, AUTHOR.ID, AUTHOR.NAME)
                .from(BOOK)
                .leftJoin(AUTHOR).on(AUTHOR.ID.equal(BOOK.AUTHOR_ID))
                .fetch(record -> {
                    var authorId = record.value3();
                    var author = new Author(authorId, record.value4());

                    var book = new Book(record.value1(), record.value2(), record.value3());
                    book.setAuthor(author);

                    return book;
                });

        var bookIds = books.stream()
                .map(Book::getId)
                .toArray(Long[]::new);

        var comments = commentsDao.fetchByBookId(bookIds);
        var genres = dsl.select(GENRE.ID, GENRE.NAME, BOOK_GENRE.BOOK_ID)
                .from(GENRE)
                .leftJoin(BOOK_GENRE).on(BOOK_GENRE.GENRE_ID.equal(GENRE.ID))
                .where(BOOK_GENRE.BOOK_ID.in(bookIds))
                .fetch(record -> new Genre(record.value1(), record.value2(), record.value3()));

        books.forEach(book -> {
            book.setGenres(genres);
            book.setComments(comments);
        });

        return books;
    }

    public Optional<Book> findById(long id) {
        var book = booksDao.findById(id);

        var authorId = book.getAuthorId();
        var author = authorsDao.fetchOneById(authorId);
        book.setAuthor(author);

        var bookId = book.getId();
        var comments = commentsDao.fetchByBookId(bookId);
        var genres = dsl.select(GENRE.ID, GENRE.NAME, BOOK_GENRE.BOOK_ID)
                .from(GENRE)
                .leftJoin(BOOK_GENRE).on(BOOK_GENRE.GENRE_ID.equal(GENRE.ID))
                .where(BOOK_GENRE.BOOK_ID.in(bookId))
                .fetch(record -> new Genre(record.value1(), record.value2(), record.value3()));

        book.setGenres(genres);
        book.setComments(comments);

        return Optional.of(book);
    }

    public void create(Book book) {
        var author = book.getAuthor();

        var authorRecord = dsl.newRecord(AUTHOR, author);
        authorRecord.store();
        var authorId = authorRecord.getId();
        book.setAuthorId(authorId);

        var bookRecord = dsl.newRecord(BOOK, book);
        bookRecord.store();
        var bookId = bookRecord.getId();

        var comments = book.getComments();
        comments.forEach(comment -> comment.setBookId(bookId));
        comments.forEach(commentsDao::insert);

        var genres = book.getGenres();
        genres.forEach(genresDao::insert);
        genres.forEach(genre -> booksGenresDao.insert(new BookGenre(null, bookId, genre.getId())));
    }

    public void update(Book book) {
        var author = book.getAuthor();
        authorsDao.update(author);

        booksDao.update(book);

        var comments = book.getComments();
        comments.forEach(commentsDao::update);

        var genres = book.getGenres();
        genres.forEach(genresDao::update);
    }

    public void delete(long id) {
        dsl.deleteFrom(COMMENT).where(COMMENT.BOOK_ID.equal(id)).execute();
        dsl.deleteFrom(BOOK_GENRE).where(BOOK_GENRE.BOOK_ID.equal(id)).execute();
        booksDao.deleteById(id);
    }
}
