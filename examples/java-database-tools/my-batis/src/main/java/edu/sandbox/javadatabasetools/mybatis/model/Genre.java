package edu.sandbox.javadatabasetools.mybatis.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Genre {

    private Long id;
    private String name;
    private Long bookId;
}
