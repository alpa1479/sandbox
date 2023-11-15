package edu.sandbox.javadatabasetools.springdatajpa.service;

import edu.sandbox.javadatabasetools.springdatajpa.model.Book;
import edu.sandbox.javadatabasetools.springdatajpa.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository repository;

    public List<Book> findAll() {
        return repository.findAll();
    }

    public Optional<Book> findById(long id) {
        return repository.findById(id);
    }

    public void create(Book book) {
        repository.save(book);
    }

    public void update(Book book) {
        repository.save(book);
    }

    public void delete(long id) {
        repository.deleteById(id);
    }
}
