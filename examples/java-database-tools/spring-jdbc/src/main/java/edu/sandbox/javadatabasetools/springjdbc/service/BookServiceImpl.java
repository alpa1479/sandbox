package edu.sandbox.javadatabasetools.springjdbc.service;

import edu.sandbox.javadatabasetools.springjdbc.converter.BookConverter;
import edu.sandbox.javadatabasetools.springjdbc.repository.BookRepository;
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

    private final BookRepository repository;
    private final BookConverter converter;

    @Override
    @Transactional(readOnly = true)
    public List<BookDto> findAll() {
        var books = repository.findAll();
        return converter.toBookDtoList(books);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BookDto> findById(Long id) {
        return repository.findById(id).map(converter::toBookDto);
    }

    @Override
    @Transactional
    public void create(BookDto bookDto) {
        var book = converter.toBook(bookDto);
        repository.create(book);
    }

    @Override
    @Transactional
    public void update(BookDto bookDto) {
        var book = converter.toBook(bookDto);
        repository.update(book);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        repository.delete(id);
    }
}
