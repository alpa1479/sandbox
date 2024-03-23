package edu.sandbox.javadatabasetools.jooq.service;

import edu.sandbox.javadatabasetools.jooq.converter.AuthorConverter;
import edu.sandbox.javadatabasetools.jooq.converter.BookConverter;
import edu.sandbox.javadatabasetools.jooq.converter.CommentConverter;
import edu.sandbox.javadatabasetools.jooq.converter.GenreConverter;
import edu.sandbox.javadatabasetools.jooq.generated.tables.daos.AuthorsDao;
import edu.sandbox.javadatabasetools.jooq.generated.tables.daos.BooksDao;
import edu.sandbox.javadatabasetools.jooq.generated.tables.daos.BooksGenresDao;
import edu.sandbox.javadatabasetools.jooq.generated.tables.daos.CommentsDao;
import edu.sandbox.javadatabasetools.jooq.generated.tables.daos.GenresDao;
import edu.sandbox.javadatabasetools.jooq.generated.tables.pojos.Authors;
import edu.sandbox.javadatabasetools.jooq.generated.tables.pojos.Books;
import edu.sandbox.javadatabasetools.jooq.generated.tables.pojos.BooksGenres;
import edu.sandbox.javadatabasetools.jooq.generated.tables.pojos.Genres;
import edu.sandbox.javadatabasetools.starter.dto.BookDto;
import edu.sandbox.javadatabasetools.starter.dto.GenreDto;
import edu.sandbox.javadatabasetools.starter.service.BookService;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static edu.sandbox.javadatabasetools.jooq.generated.tables.Authors.AUTHORS;
import static edu.sandbox.javadatabasetools.jooq.generated.tables.Books.BOOKS;
import static edu.sandbox.javadatabasetools.jooq.generated.tables.BooksGenres.BOOKS_GENRES;
import static edu.sandbox.javadatabasetools.jooq.generated.tables.Comments.COMMENTS;
import static edu.sandbox.javadatabasetools.jooq.generated.tables.Genres.GENRES;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final DSLContext dsl;
    private final BooksDao booksDao;
    private final GenresDao genresDao;
    private final AuthorsDao authorsDao;
    private final CommentsDao commentsDao;
    private final BooksGenresDao booksGenresDao;
    private final BookConverter bookConverter;
    private final AuthorConverter authorConverter;
    private final GenreConverter genreConverter;
    private final CommentConverter commentConverter;

    @Override
    public List<BookDto> findAll() {
        var books = dsl.select(BOOKS.ID, BOOKS.TITLE, AUTHORS.ID, AUTHORS.NAME)
                .from(BOOKS)
                .leftJoin(AUTHORS).on(AUTHORS.ID.equal(BOOKS.AUTHOR_ID))
                .fetch(record -> {
                    var authorId = record.value3();
                    var author = new Authors(authorId, record.value4());
                    var authorDto = authorConverter.toAuthorDto(author);

                    var book = new Books(record.value1(), record.value2(), record.value3());
                    var bookDto = bookConverter.toBookDto(book);
                    bookDto.setAuthor(authorDto);

                    return bookDto;
                });

        var bookIds = books.stream()
                .map(BookDto::getId)
                .toArray(Long[]::new);

        var comments = commentsDao.fetchByBookId(bookIds);
        comments.forEach(comment -> {
            var bookId = comment.getBookId();
            var commentDto = commentConverter.toCommentDto(comment);
            books.stream()
                    .filter(b -> b.getId().equals(bookId))
                    .findFirst()
                    .ifPresent(b -> {
                        var bookComments = b.getComments();
                        var notContains = bookComments.stream().noneMatch(c -> commentDto.id().equals(c.id()));
                        if (notContains) {
                            bookComments.add(commentDto);
                        }
                    });
        });

        dsl.select(GENRES.ID, GENRES.NAME, BOOKS_GENRES.BOOK_ID)
                .from(GENRES)
                .leftJoin(BOOKS_GENRES).on(BOOKS_GENRES.GENRE_ID.equal(GENRES.ID))
                .where(BOOKS_GENRES.BOOK_ID.in(bookIds))
                .fetch(record -> {
                    var genre = new Genres(record.value1(), record.value2());
                    var genreDto = genreConverter.toGenreDto(genre);
                    books.stream()
                            .filter(b -> b.getId().equals(record.value3()))
                            .findFirst()
                            .ifPresent(b -> {
                                var bookGenres = b.getGenres();
                                var notContains = bookGenres.stream().noneMatch(g -> genreDto.id().equals(g.id()));
                                if (notContains) {
                                    bookGenres.add(genreDto);
                                }
                            });
                    return genreDto;
                });

        return books;
    }

    @Override
    public Optional<BookDto> findById(Long id) {
        var book = booksDao.findById(id);
        var bookDto = bookConverter.toBookDto(book);

        var authorId = book.getAuthorId();
        var author = authorsDao.fetchOneById(authorId);
        var authorDto = authorConverter.toAuthorDto(author);
        bookDto.setAuthor(authorDto);

        var bookId = book.getId();
        var comments = commentsDao.fetchByBookId(bookId);
        var commentDtoList = commentConverter.toCommentDtoList(comments);
        var genres = dsl.select(GENRES.ID, GENRES.NAME, BOOKS_GENRES.BOOK_ID)
                .from(GENRES)
                .leftJoin(BOOKS_GENRES).on(BOOKS_GENRES.GENRE_ID.equal(GENRES.ID))
                .where(BOOKS_GENRES.BOOK_ID.in(bookId))
                .fetch(record -> new GenreDto(record.value1(), record.value2()));

        bookDto.setGenres(genres);
        bookDto.setComments(commentDtoList);

        return Optional.of(bookDto);
    }

    @Override
    public void create(BookDto bookDto) {
        var authorDto = bookDto.getAuthor();
        var author = authorConverter.toAuthor(authorDto);

        var authorRecord = dsl.newRecord(AUTHORS, author);
        authorRecord.store();

        var book = bookConverter.toBook(bookDto);
        var authorId = authorRecord.getId();
        book.setAuthorId(authorId);
        var bookRecord = dsl.newRecord(BOOKS, book);
        bookRecord.store();

        var bookId = bookRecord.getId();
        var commentDtoList = bookDto.getComments();
        var comments = commentConverter.toComments(commentDtoList);
        comments.forEach(comment -> comment.setBookId(bookId));
        comments.forEach(commentsDao::insert);

        var genreDtoList = bookDto.getGenres();
        var genres = genreConverter.toGenres(genreDtoList);
        genres.forEach(genresDao::insert);
        genres.forEach(genre -> booksGenresDao.insert(new BooksGenres(null, bookId, genre.getId())));
    }

    @Override
    public void update(BookDto bookDto) {
        var authorDto = bookDto.getAuthor();
        var author = authorConverter.toAuthor(authorDto);
        authorsDao.update(author);

        var book = bookConverter.toBook(bookDto);
        booksDao.update(book);

        var commentDtoList = bookDto.getComments();
        var comments = commentConverter.toComments(commentDtoList);
        comments.forEach(commentsDao::update);

        var genreDtoList = bookDto.getGenres();
        var genres = genreConverter.toGenres(genreDtoList);
        genres.forEach(genresDao::update);
    }

    @Override
    public void deleteById(Long id) {
        dsl.deleteFrom(COMMENTS).where(COMMENTS.BOOK_ID.equal(id)).execute();
        dsl.deleteFrom(BOOKS_GENRES).where(BOOKS_GENRES.BOOK_ID.equal(id)).execute();
        booksDao.deleteById(id);
    }
}
