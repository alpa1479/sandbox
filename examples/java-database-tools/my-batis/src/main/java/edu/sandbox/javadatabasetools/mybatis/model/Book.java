package edu.sandbox.javadatabasetools.mybatis.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
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

    public void setGenres(List<Genre> genres) {
        this.genres = genres.stream()
                .filter(genre -> genre.getBookId().equals(id))
                .collect(Collectors.toSet());
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments.stream()
                .filter(comment -> comment.getBookId().equals(id))
                .collect(Collectors.toSet());
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
