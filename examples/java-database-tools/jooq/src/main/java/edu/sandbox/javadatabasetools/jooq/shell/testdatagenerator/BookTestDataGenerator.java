package edu.sandbox.javadatabasetools.jooq.shell.testdatagenerator;

import edu.sandbox.javadatabasetools.jooq.generated.tables.pojos.Author;
import edu.sandbox.javadatabasetools.jooq.generated.tables.pojos.Book;
import edu.sandbox.javadatabasetools.jooq.generated.tables.pojos.Comment;
import edu.sandbox.javadatabasetools.jooq.generated.tables.pojos.Genre;
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
        var book = new Book(id, randomString(), id);
        var bookId = book.getId();

        var author = new Author(id, randomString());
        book.setAuthor(author);

        var comment = new Comment(id, bookId, randomString());
        book.addComment(comment);

        var genre = new Genre(id, randomString(), bookId);
        book.addGenre(genre);

        return book;
    }

    private static String randomString() {
        return UUID.randomUUID().toString();
    }
}
