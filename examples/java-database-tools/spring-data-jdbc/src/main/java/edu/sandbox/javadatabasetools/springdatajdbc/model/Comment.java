package edu.sandbox.javadatabasetools.springdatajdbc.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table(name = "comments")
public class Comment implements Persistable<Long> {

    @Id
    private final Long id;

    private final String text;

    @Column("book_id")
    private AggregateReference<Book, Long> bookReference;

    @Transient
    private final boolean isNew;

    @Transient
    @JsonBackReference
    private Book book;

    @PersistenceCreator
    public Comment(Long id, String text) {
        this.id = id;
        this.text = text;
        this.isNew = id == null;
    }

    public void setBook(Book book) {
        this.book = book;
        var bookId = book.getId();
        if (bookId != null) {
            this.bookReference = new AggregateReference.IdOnlyAggregateReference<>(bookId);
        }
    }
}
