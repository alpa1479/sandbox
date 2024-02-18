package edu.sandbox.javadatabasetools.springdatajdbc.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table(name = "authors")
public class Author implements Persistable<Long> {

    @Id
    private final Long id;

    private final String name;

    @Transient
    private final boolean isNew;

    @Transient
    @JsonBackReference
    private Book book;

    @PersistenceCreator
    public Author(Long id, String name) {
        this.id = id;
        this.name = name;
        this.isNew = id == null;
    }
}
