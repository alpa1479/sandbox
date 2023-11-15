package edu.sandbox.javadatabasetools.jdbc.repository;

import edu.sandbox.javadatabasetools.jdbc.core.executor.DatabaseOperations;
import edu.sandbox.javadatabasetools.jdbc.core.resultsetextractors.impl.BookResultSetExtractor;
import edu.sandbox.javadatabasetools.jdbc.model.Book;
import edu.sandbox.javadatabasetools.jdbc.model.Comment;
import edu.sandbox.javadatabasetools.jdbc.model.Genre;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BookRepository {

    private final DatabaseOperations operations;

    public Set<Book> findAll(Connection connection) {
        return operations.selectMultiple(connection, new BookResultSetExtractor(), """
                select b.id    as book_id,
                       b.title as book_title,
                       a.id    as author_id,
                       a.name  as author_name,
                       c.id    as comment_id,
                       c.text  as comment_text,
                       g.id    as genre_id,
                       g.name  as genre_name
                from books b
                         join authors a on a.id = b.author_id
                         join comments c on b.id = c.book_id
                         join books_genres bg on b.id = bg.book_id
                         join genres g on g.id = bg.genre_id;
                            """);
    }

    public Optional<Book> findById(long id, Connection connection) {
        return operations.selectSingle(connection, new BookResultSetExtractor(), """
                select b.id    as book_id,
                       b.title as book_title,
                       a.id    as author_id,
                       a.name  as author_name,
                       c.id    as comment_id,
                       c.text  as comment_text,
                       g.id    as genre_id,
                       g.name  as genre_name
                from books b
                         join authors a on a.id = b.author_id
                         join comments c on b.id = c.book_id
                         join books_genres bg on b.id = bg.book_id
                         join genres g on g.id = bg.genre_id
                where b.id = ?;
                            """, id);
    }

    public void create(Book book, Connection connection) {
        var authorName = book.getAuthor().getName();
        var authorId = operations.execute(connection, "insert into authors (name) values (?)", authorName);

        var title = book.getTitle();
        var bookId = operations.execute(connection, "insert into books (title, author_id) values (?, ?)", title, authorId);

        var comments = book.getComments();
        for (Comment comment : comments) {
            operations.execute(connection, "insert into comments (text, book_id) values (?, ?)", comment.getText(), bookId);
        }

        List<Long> genreIds = new ArrayList<>();
        var genres = book.getGenres();
        for (Genre genre : genres) {
            var genreId = operations.execute(connection, "insert into genres (name) values (?)", genre.getName());
            genreIds.add(genreId);
        }

        for (Long genreId : genreIds) {
            operations.execute(connection, "insert into books_genres (book_id, genre_id) values (?, ?)", bookId, genreId);
        }
    }

    public void update(Book book, Connection connection) {
        var authorId = book.getAuthor().getId();
        var authorName = book.getAuthor().getName();
        operations.execute(connection, "update authors set name = ? where id = ?", authorName, authorId);

        var bookId = book.getId();
        var title = book.getTitle();
        operations.execute(connection, "update books set title = ? where id = ?", title, bookId);

        var comments = book.getComments();
        for (Comment comment : comments) {
            operations.execute(connection, "update comments set text = ? where id = ?", comment.getText(), comment.getId());
        }

        var genres = book.getGenres();
        for (Genre genre : genres) {
            operations.execute(connection, "update genres set name = ? where id = ?", genre.getName(), genre.getId());
        }
    }

    public void delete(long id, Connection connection) {
        operations.execute(connection, "delete from books_genres where book_id = ?", id);
        operations.execute(connection, "delete from comments where book_id = ?", id);
        operations.execute(connection, "delete from books where id = ?", id);
    }
}
