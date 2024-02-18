package edu.sandbox.javadatabasetools.mybatis.shell;

import edu.sandbox.javadatabasetools.mybatis.service.BookService;
import edu.sandbox.javadatabasetools.mybatis.shell.formatter.JsonPrettyFormatter;
import edu.sandbox.javadatabasetools.mybatis.shell.testdatagenerator.BookTestDataGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
@RequiredArgsConstructor
public class BookShellController {

    private final BookService bookService;
    private final JsonPrettyFormatter formatter;

    @ShellMethod(value = "find all books", key = {"books", "b"})
    public String findAllBooks() {
        var books = bookService.findAll();
        return formatter.format(books);
    }

    @ShellMethod(value = "find book by id", key = {"book", "bi"})
    public String findBookById(@ShellOption(value = "-n", defaultValue = "1") long id) {
        return bookService.findById(id).map(formatter::format).orElse(null);
    }

    @ShellMethod(value = "create book", key = {"bookCreate", "bc"})
    public void createBook() {
        var book = BookTestDataGenerator.generateForCreate();
        bookService.create(book);
    }

    @ShellMethod(value = "update book", key = {"bookUpdate", "bu"})
    public void updateBook() {
        var book = BookTestDataGenerator.generateForUpdate();
        bookService.update(book);
    }

    @ShellMethod(value = "delete book", key = {"bookDelete", "bd"})
    public void deleteBook(@ShellOption(value = "-n", defaultValue = "1") long id) {
        bookService.delete(id);
    }
}
