package edu.sandbox.javadatabasetools.hibernate.service;

import edu.sandbox.javadatabasetools.hibernate.core.transaction.TransactionRunner;
import edu.sandbox.javadatabasetools.hibernate.model.Book;
import edu.sandbox.javadatabasetools.hibernate.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository repository;
    private final TransactionRunner runner;

    public List<Book> findAll() {
        return runner.transaction(repository::findAll);
    }

    public Optional<Book> findById(long id) {
        return runner.transaction(entityManager -> repository.findById(entityManager, id));
    }

    public void create(Book book) {
        runner.doInTransaction(entityManager -> repository.create(entityManager, book));
    }

    public void update(Book book) {
        runner.doInTransaction(entityManager -> repository.update(entityManager, book));
    }

    public void delete(long id) {
        runner.doInTransaction(entityManager -> repository.delete(entityManager, id));
    }
}
