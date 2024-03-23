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
import java.util.stream.Stream;

public class BookResultSetExtractor implements ResultSetExtractor<Stream<Book>> {

    private final Map<Long, Book> bookMap = new HashMap<>();
    private final Map<Long, Genre> genreMap = new HashMap<>();

    @Override
    public Stream<Book> extractData(ResultSet resultSet) throws SQLException {
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
                resultSet.getString("author_name")
        );
        book.setAuthor(author);
    }

    private void extractComment(ResultSet resultSet, Book book) throws SQLException {
        var comment = new Comment(
                resultSet.getLong("comment_id"),
                resultSet.getString("comment_text")
        );
        addCommentIfMissing(book, comment);
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
        addGenreIfMissing(book, genre);
    }

    private void addCommentIfMissing(Book book, Comment comment) {
        var comments = book.getComments();

        var notContains = comments
                .stream()
                .noneMatch(c -> c.id().equals(comment.id()));

        if (notContains) {
            comments.add(comment);
        }
    }

    private void addGenreIfMissing(Book book, Genre genre) {
        var genres = book.getGenres();

        var notContains = genres
                .stream()
                .noneMatch(g -> g.id().equals(genre.id()));

        if (notContains) {
            genres.add(genre);
        }
    }
}
