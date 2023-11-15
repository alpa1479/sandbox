package edu.sandbox.javadatabasetools.jdbc.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Data
public class Book {

    private Long id;
    private String title;
    @JsonManagedReference
    private Author author;
    @JsonManagedReference
    private Set<Comment> comments = new LinkedHashSet<>();
    @JsonManagedReference
    private Set<Genre> genres = new LinkedHashSet<>();

    public Book(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public void addGenre(Genre genre) {
        genres.add(genre);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
