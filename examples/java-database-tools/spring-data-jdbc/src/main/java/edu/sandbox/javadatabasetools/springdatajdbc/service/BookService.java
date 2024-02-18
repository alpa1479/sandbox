package edu.sandbox.javadatabasetools.springdatajdbc.service;

import edu.sandbox.javadatabasetools.springdatajdbc.model.Book;
import edu.sandbox.javadatabasetools.springdatajdbc.repository.AuthorRepository;
import edu.sandbox.javadatabasetools.springdatajdbc.repository.BookGenreRelationRepository;
import edu.sandbox.javadatabasetools.springdatajdbc.repository.BookRepository;
import edu.sandbox.javadatabasetools.springdatajdbc.repository.CommentRepository;
import edu.sandbox.javadatabasetools.springdatajdbc.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CommentRepository commentRepository;
    private final GenreRepository genreRepository;
    private final BookGenreRelationRepository bookGenreRelationRepository;

    public List<Book> findAll() {
        return bookRepository.findAllWithReferences();
    }

    public Optional<Book> findById(long id) {
        return bookRepository.findByIdWithReferences(id);
    }

    @Transactional
    public void create(Book book) {
        var author = book.getAuthor();
        author = authorRepository.save(author);
        book.setAuthor(author);

        var genres = book.getGenres();
        genres = genreRepository.saveAll(genres);
        book.setGenres(genres);

        var savedBook = bookRepository.save(book);
        var savedBookId = savedBook.getId();
        book.setId(savedBookId);
        book.getComments().forEach(comment -> comment.setBook(savedBook));

        var bookGenreRelations = book.getBookGenreRelations();
        bookGenreRelationRepository.saveAll(bookGenreRelations);

        var comments = book.getComments();
        commentRepository.saveAll(comments);
    }

    @Transactional
    public void update(Book book) {
        bookRepository.save(book);

        var author = book.getAuthor();
        authorRepository.save(author);

        var genres = book.getGenres();
        genreRepository.saveAll(genres);

        var comments = book.getComments();
        commentRepository.saveAll(comments);
    }

    @Transactional
    public void delete(long id) {
        commentRepository.deleteAllByBookId(id);
        bookGenreRelationRepository.deleteAllByBookId(id);
        bookRepository.deleteById(id);
    }
}
