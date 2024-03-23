package edu.sandbox.javadatabasetools.starter.controller;

import edu.sandbox.javadatabasetools.starter.dto.BookDto;
import edu.sandbox.javadatabasetools.starter.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/api/v1/books")
    public List<BookDto> findAll() {
        return bookService.findAll();
    }

    @GetMapping("/api/v1/books/{id}")
    public BookDto findById(@PathVariable Long id) {
        return bookService.findById(id).orElse(null);
    }

    @PostMapping("/api/v1/books")
    public void create(@RequestBody BookDto book) {
        bookService.create(book);
    }

    @PutMapping("/api/v1/books")
    public void update(@RequestBody BookDto book) {
        bookService.update(book);
    }

    @DeleteMapping("/api/v1/books/{id}")
    public void deleteById(@PathVariable Long id) {
        bookService.deleteById(id);
    }
}
