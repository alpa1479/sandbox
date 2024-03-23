package edu.sandbox.javadatabasetools.springjdbc.repository;

import edu.sandbox.javadatabasetools.springjdbc.core.resultsetextractors.BookResultSetExtractor;
import edu.sandbox.javadatabasetools.springjdbc.model.Book;
import edu.sandbox.javadatabasetools.springjdbc.model.Comment;
import edu.sandbox.javadatabasetools.springjdbc.model.Genre;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Map.of;

@Service
@RequiredArgsConstructor
public class BookRepository {

    private final NamedParameterJdbcOperations operations;

    public List<Book> findAll() {
        return operations.query("""
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
                                    """, new BookResultSetExtractor())
                .collect(Collectors.toList());
    }

    public Optional<Book> findById(long id) {
        return operations.query("""
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
                        where b.id = :id;
                                    """, of("id", id), new BookResultSetExtractor())
                .findFirst();
    }

    public void create(Book book) {
        var keyHolder = new GeneratedKeyHolder();

        var authorName = book.getAuthor().name();
        operations.update("insert into authors (name) values (:name)",
                new MapSqlParameterSource(of("name", authorName)), keyHolder, new String[]{"id"});
        var authorId = keyHolder.getKey().longValue();

        var title = book.getTitle();
        operations.update("insert into books (title, author_id) values (:title, :author_id)",
                new MapSqlParameterSource(of("title", title, "author_id", authorId)), keyHolder, new String[]{"id"});
        var bookId = keyHolder.getKey().longValue();

        var comments = book.getComments();
        for (Comment comment : comments) {
            operations.update("insert into comments (text, book_id) values (:text, :book_id)",
                    of("text", comment.text(), "book_id", bookId));
        }

        List<Long> genreIds = new ArrayList<>();
        var genres = book.getGenres();
        for (Genre genre : genres) {
            operations.update("insert into genres (name) values (:name)", new MapSqlParameterSource(of("name", genre.name())), keyHolder, new String[]{"id"});
            genreIds.add(keyHolder.getKey().longValue());
        }

        for (Long genreId : genreIds) {
            operations.update("insert into books_genres (book_id, genre_id) values (:book_id, :genre_id)",
                    of("book_id", bookId, "genre_id", genreId));
        }
    }

    public void update(Book book) {
        var authorId = book.getAuthor().id();
        var authorName = book.getAuthor().name();
        operations.update("update authors set name = :name where id = :id", of("name", authorName, "id", authorId));

        var bookId = book.getId();
        var title = book.getTitle();
        operations.update("update books set title = :title where id = :id", of("title", title, "id", bookId));

        var comments = book.getComments();
        for (Comment comment : comments) {
            operations.update("update comments set text = :text where id = :id", of("text", comment.text(), "id", comment.id()));
        }

        var genres = book.getGenres();
        for (Genre genre : genres) {
            operations.update("update genres set name = :name where id = :id", of("name", genre.name(), "id", genre.id()));
        }
    }

    public void delete(long id) {
        operations.update("delete from books_genres where book_id = :id", of("id", id));
        operations.update("delete from comments where book_id = :id", of("id", id));
        operations.update("delete from books where id = :id", of("id", id));
    }
}
