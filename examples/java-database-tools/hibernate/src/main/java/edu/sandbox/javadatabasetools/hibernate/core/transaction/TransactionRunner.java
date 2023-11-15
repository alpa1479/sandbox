package edu.sandbox.javadatabasetools.hibernate.core.transaction;

import edu.sandbox.javadatabasetools.hibernate.exception.DatabaseOperationException;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransactionRunner {

    private final EntityManagerFactory entityManagerFactory;

    public <T> T transaction(TransactionFunction<T> function) {
        try (var entityManager = entityManagerFactory.createEntityManager()) {
            var transaction = entityManager.getTransaction();
            try {
                transaction.begin();
                var result = function.apply(entityManager);
                transaction.commit();
                return result;
            } catch (Exception ex) {
                transaction.rollback();
                throw new DatabaseOperationException(ex);
            }
        }
    }

    public void doInTransaction(TransactionConsumer consumer) {
        try (var entityManager = entityManagerFactory.createEntityManager()) {
            var transaction = entityManager.getTransaction();
            try {
                transaction.begin();
                consumer.accept(entityManager);
                transaction.commit();
            } catch (Exception ex) {
                transaction.rollback();
                throw new DatabaseOperationException(ex);
            }
        }
    }
}
