package edu.sandbox.javadatabasetools.springdatajpa.repository;

import edu.sandbox.javadatabasetools.springdatajpa.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
