package edu.sandbox.javadatabasetools.hibernate.repository;

import edu.sandbox.javadatabasetools.hibernate.model.Book;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Service
public class BookRepository {

    public List<Book> findAll(EntityManager entityManager) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var query = criteriaBuilder.createQuery(Book.class);
        query.from(Book.class);
        return entityManager.createQuery(query).getResultList();
    }

    public Optional<Book> findById(EntityManager entityManager, Long id) {
        return ofNullable(entityManager.find(Book.class, id));
    }

    public void create(EntityManager entityManager, Book book) {
        entityManager.persist(book);
    }

    public void update(EntityManager entityManager, Book book) {
        entityManager.merge(book);
    }

    public void delete(EntityManager entityManager, Long id) {
        var book = entityManager.find(Book.class, id);
        entityManager.remove(book);
    }
}
