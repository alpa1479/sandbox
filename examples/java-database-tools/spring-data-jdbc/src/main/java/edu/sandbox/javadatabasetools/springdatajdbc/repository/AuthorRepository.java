package edu.sandbox.javadatabasetools.springdatajdbc.repository;

import edu.sandbox.javadatabasetools.springdatajdbc.model.Author;
import org.springframework.data.repository.ListCrudRepository;

public interface AuthorRepository extends ListCrudRepository<Author, Long> {
}
