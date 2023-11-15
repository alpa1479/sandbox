package edu.sandbox.javadatabasetools.jdbc.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@Data
@RequiredArgsConstructor
public class Comment {

    private final Long id;
    private final String text;
    @JsonBackReference
    private final Book book;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return Objects.equals(id, comment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
