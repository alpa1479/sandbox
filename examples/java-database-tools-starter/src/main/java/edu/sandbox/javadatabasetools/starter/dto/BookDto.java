package edu.sandbox.javadatabasetools.starter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {

    private Long id;
    private String title;
    private AuthorDto author;
    private List<CommentDto> comments = new ArrayList<>();
    private List<GenreDto> genres = new ArrayList<>();
}
