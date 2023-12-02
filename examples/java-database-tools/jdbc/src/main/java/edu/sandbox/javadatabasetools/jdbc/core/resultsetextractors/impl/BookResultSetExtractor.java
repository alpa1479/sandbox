package edu.sandbox.javadatabasetools.jdbc.core.resultsetextractors.impl;

import edu.sandbox.javadatabasetools.jdbc.core.resultsetextractors.ResultSetExtractor;
import edu.sandbox.javadatabasetools.jdbc.model.Author;
import edu.sandbox.javadatabasetools.jdbc.model.Book;
import edu.sandbox.javadatabasetools.jdbc.model.Comment;
import edu.sandbox.javadatabasetools.jdbc.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class BookResultSetExtractor implements ResultSetExtractor<Book> {

    private final Map<Long, Book> bookMap = new HashMap<>();
    private final Map<Long, Genre> genreMap = new HashMap<>();

    @Override
    public Stream<Book> extract(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            var bookId = resultSet.getLong("book_id");
            if (bookMap.containsKey(bookId)) {
                var book = bookMap.get(bookId);

                extractComment(resultSet, book);
                extractGenre(resultSet, book);
            } else {
                var book = new Book(bookId, resultSet.getString("book_title"));

                extractAuthor(resultSet, book);
                extractComment(resultSet, book);
                extractGenre(resultSet, book);

                bookMap.put(book.getId(), book);
            }
        }
        return bookMap.values().stream();
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
