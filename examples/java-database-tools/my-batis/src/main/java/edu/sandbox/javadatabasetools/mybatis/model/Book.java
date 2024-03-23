package edu.sandbox.javadatabasetools.mybatis.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    private Long id;
    private String title;
    private Author author;
    private Set<Comment> comments = new LinkedHashSet<>();
    private Set<Genre> genres = new LinkedHashSet<>();

    public void setGenres(List<Genre> genres) {
        this.genres = genres.stream()
                .filter(genre -> genre.getBookId() == null || genre.getBookId().equals(id))
                .collect(Collectors.toSet());
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments.stream()
                .filter(comment -> comment.getBookId() == null || comment.getBookId().equals(id))
                .collect(Collectors.toSet());
    }
}
