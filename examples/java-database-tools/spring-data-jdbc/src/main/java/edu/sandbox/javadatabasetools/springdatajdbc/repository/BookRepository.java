package edu.sandbox.javadatabasetools.springdatajdbc.repository;

import edu.sandbox.javadatabasetools.springdatajdbc.model.Book;
import edu.sandbox.javadatabasetools.springdatajdbc.resultsetextractor.BookWithReferencesListResultSetExtractor;
import edu.sandbox.javadatabasetools.springdatajdbc.resultsetextractor.BookWithReferencesResultSetExtractor;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends ListCrudRepository<Book, Long> {

    @Query(value = """
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
            """, resultSetExtractorClass = BookWithReferencesListResultSetExtractor.class)
    List<Book> findAllWithReferences();

    @Query(value = """
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
            """, resultSetExtractorClass = BookWithReferencesResultSetExtractor.class)
    Optional<Book> findByIdWithReferences(@Param("id") long id);
}
