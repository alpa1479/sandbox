package edu.sandbox.javadatabasetools.springdatajdbc.resultsetextractor;

import edu.sandbox.javadatabasetools.springdatajdbc.model.Author;
import edu.sandbox.javadatabasetools.springdatajdbc.model.Book;
import edu.sandbox.javadatabasetools.springdatajdbc.model.Comment;
import edu.sandbox.javadatabasetools.springdatajdbc.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class AbstractBookWithReferencesResultSetExtractor {

    private final Map<Long, Book> bookMap = new HashMap<>();
    private final Map<Long, Genre> genreMap = new HashMap<>();
    private final Map<Long, Comment> commentMap = new HashMap<>();

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
                resultSet.getString("author_name")
        );
        book.setAuthor(author);
    }

    private void extractComment(ResultSet resultSet, Book book) throws SQLException {
        var commentId = resultSet.getLong("comment_id");
        if (!commentMap.containsKey(commentId)) {
            var comment = new Comment(
                    commentId,
                    resultSet.getString("comment_text")
            );
            commentMap.put(commentId, comment);
            comment.setBook(book);
            addCommentIfMissing(book, comment);
        }
    }

    private void extractGenre(ResultSet resultSet, Book book) throws SQLException {
        var genreId = resultSet.getLong("genre_id");
        var genre = genreMap.get(genreId);
        if (genre == null) {
            genre = new Genre(
                    genreId,
                    resultSet.getString("genre_name")
            );
            genreMap.put(genreId, genre);
        }
        addGenreIfMissing(book, genre);
    }

    private void addCommentIfMissing(Book book, Comment comment) {
        var comments = book.getComments();

        var notContains = comments
                .stream()
                .noneMatch(c -> c.getId() != null && c.getId().equals(comment.getId()));

        if (notContains) {
            comments.add(comment);
        }
    }

    private void addGenreIfMissing(Book book, Genre genre) {
        var genres = book.getGenres();

        var notContains = genres
                .stream()
                .noneMatch(g -> g.getId() != null && g.getId().equals(genre.getId()));

        if (notContains) {
            genres.add(genre);
        }
    }
}
