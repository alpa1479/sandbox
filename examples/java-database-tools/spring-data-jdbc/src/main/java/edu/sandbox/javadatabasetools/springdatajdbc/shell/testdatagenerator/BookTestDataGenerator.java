package edu.sandbox.javadatabasetools.springdatajdbc.shell.testdatagenerator;

import edu.sandbox.javadatabasetools.springdatajdbc.model.Author;
import edu.sandbox.javadatabasetools.springdatajdbc.model.Book;
import edu.sandbox.javadatabasetools.springdatajdbc.model.Comment;
import edu.sandbox.javadatabasetools.springdatajdbc.model.Genre;
import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class BookTestDataGenerator {

    public static Book generateForCreate() {
        return generate(null);
    }

    public static Book generateForUpdate() {
        return generate(1L);
    }

    private static Book generate(Long id) {
        var book = new Book(id, randomString());
        var author = new Author(id, randomString());
        book.setAuthor(author);
        author.setBook(book);

        var comment = new Comment(id, randomString());
        book.addComment(comment);
        comment.setBook(book);

        var genre = new Genre(id, randomString());
        book.addGenre(genre);
        genre.addBook(book);

        return book;
    }

    private static String randomString() {
        return UUID.randomUUID().toString();
    }
}
