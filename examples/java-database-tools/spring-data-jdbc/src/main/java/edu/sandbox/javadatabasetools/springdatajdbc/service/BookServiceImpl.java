package edu.sandbox.javadatabasetools.springdatajdbc.service;

import edu.sandbox.javadatabasetools.springdatajdbc.converter.BookConverter;
import edu.sandbox.javadatabasetools.springdatajdbc.repository.AuthorRepository;
import edu.sandbox.javadatabasetools.springdatajdbc.repository.BookGenreRelationRepository;
import edu.sandbox.javadatabasetools.springdatajdbc.repository.BookRepository;
import edu.sandbox.javadatabasetools.springdatajdbc.repository.CommentRepository;
import edu.sandbox.javadatabasetools.springdatajdbc.repository.GenreRepository;
import edu.sandbox.javadatabasetools.starter.dto.BookDto;
import edu.sandbox.javadatabasetools.starter.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CommentRepository commentRepository;
    private final GenreRepository genreRepository;
    private final BookGenreRelationRepository bookGenreRelationRepository;
    private final BookConverter converter;

    @Override
    public List<BookDto> findAll() {
        var books = bookRepository.findAllWithReferences();
        return converter.toBookDtoList(books);
    }

    @Override
    public Optional<BookDto> findById(Long id) {
        return bookRepository.findByIdWithReferences(id).map(converter::toBookDto);
    }

    @Override
    @Transactional
    public void create(BookDto bookDto) {
        var book = converter.toBook(bookDto);

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

    @Override
    @Transactional
    public void update(BookDto bookDto) {
        var book = converter.toBook(bookDto);
        bookRepository.save(book);

        var author = book.getAuthor();
        authorRepository.save(author);

        var genres = book.getGenres();
        genreRepository.saveAll(genres);

        var comments = book.getComments();
        commentRepository.saveAll(comments);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        commentRepository.deleteAllByBookId(id);
        bookGenreRelationRepository.deleteAllByBookId(id);
        bookRepository.deleteById(id);
    }
}
