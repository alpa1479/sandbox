package edu.sandbox.javadatabasetools.springdatajdbc.repository;

import edu.sandbox.javadatabasetools.springdatajdbc.model.BookGenreRelation;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

public interface BookGenreRelationRepository extends ListCrudRepository<BookGenreRelation, Long> {

    @Modifying
    @Query("delete from books_genres where book_id = :book_id")
    void deleteAllByBookId(@Param("book_id") Long bookId);
}
