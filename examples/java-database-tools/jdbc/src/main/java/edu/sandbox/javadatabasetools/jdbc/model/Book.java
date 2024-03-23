package edu.sandbox.javadatabasetools.jdbc.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Book {

    private Long id;
    private String title;
    private Author author;
    private List<Comment> comments = new ArrayList<>();
    private List<Genre> genres = new ArrayList<>();

    public Book(Long id, String title) {
        this.id = id;
        this.title = title;
    }
}
