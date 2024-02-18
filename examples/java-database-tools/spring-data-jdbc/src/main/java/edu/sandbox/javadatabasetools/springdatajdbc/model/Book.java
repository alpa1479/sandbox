package edu.sandbox.javadatabasetools.springdatajdbc.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Table(name = "books")
public class Book implements Persistable<Long> {

    @Id
    private Long id;

    private final String title;

    @Transient
    private final boolean isNew;

    @Column("author_id")
    private AggregateReference<Author, Long> authorReference;

    @Transient
    @JsonManagedReference
    private Author author;

    @Transient
    @JsonManagedReference
    private List<Comment> comments = new ArrayList<>();

    @Transient
    @JsonManagedReference
    private List<Genre> genres = new ArrayList<>();

    @PersistenceCreator
    public Book(Long id, String title) {
        this.id = id;
        this.title = title;
        this.isNew = id == null;
    }

    public List<BookGenreRelation> getBookGenreRelations() {
        return genres.stream()
                .map(genre -> new BookGenreRelation(null, id, genre.getId()))
                .toList();
    }

    public void setAuthor(Author author) {
        this.author = author;
        var authorId = author.getId();
        if (authorId != null) {
            this.authorReference = new AggregateReference.IdOnlyAggregateReference<>(authorId);
        }
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public void addGenre(Genre genre) {
        genres.add(genre);
    }

    public boolean hasGenre(Genre targetGenre) {
        return genres.stream()
                .map(Genre::getId)
                .filter(Objects::nonNull)
                .anyMatch(id -> id.equals(targetGenre.getId()));
    }
}
