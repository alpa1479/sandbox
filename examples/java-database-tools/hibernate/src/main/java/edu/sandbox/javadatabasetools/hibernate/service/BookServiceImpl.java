package edu.sandbox.javadatabasetools.hibernate.service;

import edu.sandbox.javadatabasetools.hibernate.converter.BookConverter;
import edu.sandbox.javadatabasetools.hibernate.core.transaction.TransactionRunner;
import edu.sandbox.javadatabasetools.hibernate.repository.BookRepository;
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
        return runner.transaction(entityManager -> {
            var books = repository.findAll(entityManager);
            return converter.toBookDtoList(books);
        });
    }

    @Override
    public Optional<BookDto> findById(Long id) {
        return runner.transaction(entityManager -> repository.findById(entityManager, id).map(converter::toBookDto));
    }

    @Override
    public void create(BookDto bookDto) {
        runner.doInTransaction(entityManager -> {
            var book = converter.toBook(bookDto);
            repository.create(entityManager, book);
        });
    }

    @Override
    public void update(BookDto bookDto) {
        runner.doInTransaction(entityManager -> {
            var book = converter.toBook(bookDto);
            repository.update(entityManager, book);
        });
    }

    @Override
    public void deleteById(Long id) {
        runner.doInTransaction(entityManager -> repository.delete(entityManager, id));
    }
}
