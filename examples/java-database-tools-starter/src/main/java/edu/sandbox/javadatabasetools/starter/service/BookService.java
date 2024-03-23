package edu.sandbox.javadatabasetools.starter.service;

import edu.sandbox.javadatabasetools.starter.dto.BookDto;

import java.util.List;
import java.util.Optional;

public interface BookService {

    List<BookDto> findAll();

    Optional<BookDto> findById(Long id);

    void create(BookDto bookDto);

    void update(BookDto bookDto);

    void deleteById(Long id);
}
