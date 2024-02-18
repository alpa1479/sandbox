package edu.sandbox.javadatabasetools.springdatajdbc.repository;

import edu.sandbox.javadatabasetools.springdatajdbc.model.Genre;
import org.springframework.data.repository.ListCrudRepository;

public interface GenreRepository extends ListCrudRepository<Genre, Long> {
}
