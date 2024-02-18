package edu.sandbox.javadatabasetools.mybatis.shell.testdatagenerator;

import edu.sandbox.javadatabasetools.mybatis.model.Author;
import edu.sandbox.javadatabasetools.mybatis.model.Book;
import edu.sandbox.javadatabasetools.mybatis.model.Comment;
import edu.sandbox.javadatabasetools.mybatis.model.Genre;
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
        var bookId = book.getId();

        var author = new Author(id, randomString(), book);
        book.setAuthor(author);

        var comment = new Comment(id, randomString(), bookId, book);
        book.addComment(comment);

        var genre = new Genre(id, randomString(), bookId);
        genre.addBook(book);
        book.addGenre(genre);

        return book;
    }

    private static String randomString() {
        return UUID.randomUUID().toString();
    }
}
