package edu.sandbox.javadatabasetools.jdbc.service;

import edu.sandbox.javadatabasetools.jdbc.core.transaction.TransactionRunner;
import edu.sandbox.javadatabasetools.jdbc.model.Book;
import edu.sandbox.javadatabasetools.jdbc.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BookService {

    private final TransactionRunner runner;
    private final BookRepository repository;

    public Set<Book> findAll() {
        return runner.transaction(repository::findAll);
    }

    public Optional<Book> findById(long id) {
        return runner.transaction(connection -> repository.findById(id, connection));
    }

    public void create(Book book) {
        runner.doInTransaction(connection -> repository.create(book, connection));
    }

    public void update(Book book) {
        runner.doInTransaction(connection -> repository.update(book, connection));
    }

    public void delete(long id) {
        runner.doInTransaction(connection -> repository.delete(id, connection));
    }
}
