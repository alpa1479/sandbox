package edu.sandbox.javadatabasetools.springjdbc.service;

import edu.sandbox.javadatabasetools.springjdbc.model.Book;
import edu.sandbox.javadatabasetools.springjdbc.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository repository;

    @Transactional(readOnly = true)
    public Set<Book> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Book> findById(long id) {
        return repository.findById(id);
    }

    @Transactional
    public void create(Book book) {
        repository.create(book);
    }

    @Transactional
    public void update(Book book) {
        repository.update(book);
    }

    @Transactional
    public void delete(long id) {
        repository.delete(id);
    }
}
