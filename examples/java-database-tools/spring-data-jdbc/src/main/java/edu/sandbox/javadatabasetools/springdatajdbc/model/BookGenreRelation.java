package edu.sandbox.javadatabasetools.springdatajdbc.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table(name = "books_genres")
public class BookGenreRelation implements Persistable<Long> {

    @Id
    private final Long id;

    private final Long bookId;

    private final Long genreId;

    @Transient
    private final boolean isNew;

    @PersistenceCreator
    public BookGenreRelation(Long id, Long bookId, Long genreId) {
        this.id = id;
        this.bookId = bookId;
        this.genreId = genreId;
        this.isNew = id == null;
    }
}
