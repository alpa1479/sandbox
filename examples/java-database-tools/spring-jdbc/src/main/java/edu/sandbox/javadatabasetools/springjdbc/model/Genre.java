package edu.sandbox.javadatabasetools.springjdbc.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Data
@RequiredArgsConstructor
public class Genre {

    private final Long id;
    private final String name;
    @JsonBackReference
    private final Set<Book> books = new LinkedHashSet<>();

    public void addBook(Book book) {
        books.add(book);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genre genre = (Genre) o;
        return Objects.equals(id, genre.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
