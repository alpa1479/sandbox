package edu.sandbox.javadatabasetools.jdbc.service;

import edu.sandbox.javadatabasetools.jdbc.converter.BookConverter;
import edu.sandbox.javadatabasetools.jdbc.core.transaction.TransactionRunner;
import edu.sandbox.javadatabasetools.jdbc.repository.BookRepository;
import edu.sandbox.javadatabasetools.starter.dto.BookDto;
import edu.sandbox.javadatabasetools.starter.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final TransactionRunner runner;
    private final BookRepository repository;
    private final BookConverter converter;

    @Override
    public List<BookDto> findAll() {
        return runner.transaction(connection -> {
            var books = repository.findAll(connection);
            return converter.toBookDtoList(books);
        });
    }

    @Override
    public Optional<BookDto> findById(Long id) {
        return runner.transaction(connection -> repository.findById(id, connection).map(converter::toBookDto));
    }

    @Override
    public void create(BookDto bookDto) {
        runner.doInTransaction(connection -> {
            var book = converter.toBook(bookDto);
            repository.create(book, connection);
        });
    }

    @Override
    public void update(BookDto bookDto) {
        runner.doInTransaction(connection -> {
            var book = converter.toBook(bookDto);
            repository.update(book, connection);
        });
    }

    @Override
    public void deleteById(Long id) {
        runner.doInTransaction(connection -> repository.delete(id, connection));
    }
}
