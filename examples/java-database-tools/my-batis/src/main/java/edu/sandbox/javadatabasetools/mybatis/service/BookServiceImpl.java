package edu.sandbox.javadatabasetools.mybatis.service;

import edu.sandbox.javadatabasetools.mybatis.converter.BookConverter;
import edu.sandbox.javadatabasetools.mybatis.mapper.AuthorMapper;
import edu.sandbox.javadatabasetools.mybatis.mapper.BookMapper;
import edu.sandbox.javadatabasetools.mybatis.mapper.CommentMapper;
import edu.sandbox.javadatabasetools.mybatis.mapper.GenreMapper;
import edu.sandbox.javadatabasetools.mybatis.model.Book;
import edu.sandbox.javadatabasetools.starter.dto.BookDto;
import edu.sandbox.javadatabasetools.starter.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookMapper bookMapper;
    private final AuthorMapper authorMapper;
    private final CommentMapper commentMapper;
    private final GenreMapper genreMapper;
    private final BookConverter converter;

    @Override
    public List<BookDto> findAll() {
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

        return converter.toBookDtoList(books);
    }

    @Override
    public Optional<BookDto> findById(Long id) {
        var book = bookMapper.findById(id);

        var bookId = book.getId();

        var comments = commentMapper.findBookComments(bookId);
        var genres = genreMapper.findBookGenres(bookId);

        book.setGenres(genres);
        book.setComments(comments);

        return Optional.of(converter.toBookDto(book));
    }

    @Override
    public void create(BookDto bookDto) {
        var book = converter.toBook(bookDto);

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

    @Override
    public void update(BookDto bookDto) {
        var book = converter.toBook(bookDto);

        var author = book.getAuthor();
        authorMapper.update(author);

        bookMapper.update(book);

        var comments = book.getComments();
        comments.forEach(commentMapper::update);

        var genres = book.getGenres();
        genres.forEach(genreMapper::update);
    }

    @Override
    public void deleteById(Long id) {
        commentMapper.deleteByBookId(id);
        genreMapper.deleteBookGenres(id);
        bookMapper.delete(id);
    }
}
