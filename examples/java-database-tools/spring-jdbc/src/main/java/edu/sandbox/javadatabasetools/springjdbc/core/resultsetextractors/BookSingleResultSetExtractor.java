package edu.sandbox.javadatabasetools.springjdbc.core.resultsetextractors;

import edu.sandbox.javadatabasetools.springjdbc.model.Author;
import edu.sandbox.javadatabasetools.springjdbc.model.Book;
import edu.sandbox.javadatabasetools.springjdbc.model.Comment;
import edu.sandbox.javadatabasetools.springjdbc.model.Genre;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.util.Optional.ofNullable;

public class BookSingleResultSetExtractor implements ResultSetExtractor<Optional<Book>> {

    private final Map<Long, Book> bookMap = new HashMap<>();
    private final Map<Long, Genre> genreMap = new HashMap<>();

    @Override
    public Optional<Book> extractData(ResultSet resultSet) throws SQLException {
        Book book = null;
        if (resultSet.next()) {
            var bookId = resultSet.getLong("book_id");
            if (bookMap.containsKey(bookId)) {
                book = bookMap.get(bookId);

                extractComment(resultSet, book);
                extractGenre(resultSet, book);
            } else {
                book = new Book(bookId, resultSet.getString("book_title"));

                extractAuthor(resultSet, book);
                extractComment(resultSet, book);
                extractGenre(resultSet, book);

                bookMap.put(book.getId(), book);
            }
        }
        return ofNullable(book);
    }

    private void extractAuthor(ResultSet resultSet, Book book) throws SQLException {
        var author = new Author(
                resultSet.getLong("author_id"),
                resultSet.getString("author_name"),
                book
        );
        book.setAuthor(author);
    }

    private void extractComment(ResultSet resultSet, Book book) throws SQLException {
        var comment = new Comment(
                resultSet.getLong("comment_id"),
                resultSet.getString("comment_text"),
                book
        );
        book.addComment(comment);
    }

    private void extractGenre(ResultSet resultSet, Book book) throws SQLException {
        var genreId = resultSet.getLong("genre_id");
        Genre genre;
        if (genreMap.containsKey(genreId)) {
            genre = genreMap.get(genreId);
        } else {
            genre = new Genre(
                    genreId,
                    resultSet.getString("genre_name")
            );
        }
        genre.addBook(book);
        book.addGenre(genre);
    }
}
