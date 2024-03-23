package edu.sandbox.javadatabasetools.springdatajpa.service;

import edu.sandbox.javadatabasetools.springdatajpa.converter.BookConverter;
import edu.sandbox.javadatabasetools.springdatajpa.repository.BookRepository;
import edu.sandbox.javadatabasetools.starter.dto.BookDto;
import edu.sandbox.javadatabasetools.starter.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository repository;
    private final BookConverter converter;

    @Override
    public List<BookDto> findAll() {
        var books = repository.findAll();
        return converter.toBookDtoList(books);
    }

    @Override
    public Optional<BookDto> findById(Long id) {
        return repository.findById(id).map(converter::toBookDto);
    }

    @Override
    public void create(BookDto bookDto) {
        var book = converter.toBook(bookDto);
        repository.save(book);
    }

    @Override
    public void update(BookDto bookDto) {
        var book = converter.toBook(bookDto);
        repository.save(book);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
