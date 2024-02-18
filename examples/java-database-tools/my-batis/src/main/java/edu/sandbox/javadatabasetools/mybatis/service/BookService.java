package edu.sandbox.javadatabasetools.mybatis.service;

import edu.sandbox.javadatabasetools.mybatis.mapper.AuthorMapper;
import edu.sandbox.javadatabasetools.mybatis.mapper.BookMapper;
import edu.sandbox.javadatabasetools.mybatis.mapper.CommentMapper;
import edu.sandbox.javadatabasetools.mybatis.mapper.GenreMapper;
import edu.sandbox.javadatabasetools.mybatis.model.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookMapper bookMapper;
    private final AuthorMapper authorMapper;
    private final CommentMapper commentMapper;
    private final GenreMapper genreMapper;

    public Set<Book> findAll() {
        var books = bookMapper.findAll();

        var bookIds = books.stream()
                .mapToLong(Book::getId)
                .toArray();

        var comments = commentMapper.findBookComments(bookIds);
        var genres = genreMapper.findBookGenres(bookIds);

        books.forEach(book -> {
            book.setGenres(genres);
            book.setComments(comments);
        });

        return books;
    }

    public Optional<Book> findById(long id) {
        var book = bookMapper.findById(id);

        var bookId = book.getId();

        var comments = commentMapper.findBookComments(bookId);
        var genres = genreMapper.findBookGenres(bookId);

        book.setGenres(genres);
        book.setComments(comments);

        return Optional.of(book);
    }

    public void create(Book book) {
        var author = book.getAuthor();
        authorMapper.create(author);

        bookMapper.create(book);
        var bookId = book.getId();

        var comments = book.getComments();
        comments.forEach(comment -> comment.setBookId(bookId));
        comments.forEach(commentMapper::create);

        var genres = book.getGenres();
        genres.forEach(genreMapper::create);
        genres.forEach(genre -> genreMapper.createBookGenre(bookId, genre.getId()));
    }

    public void update(Book book) {
        var author = book.getAuthor();
        authorMapper.update(author);

        bookMapper.update(book);

        var comments = book.getComments();
        comments.forEach(commentMapper::update);

        var genres = book.getGenres();
        genres.forEach(genreMapper::update);
    }

    public void delete(long id) {
        commentMapper.deleteByBookId(id);
        genreMapper.deleteBookGenres(id);
        bookMapper.delete(id);
    }
}
