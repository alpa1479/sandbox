package edu.sandbox.javadatabasetools.hibernate.core.transaction;

import jakarta.persistence.EntityManager;

import java.util.function.Consumer;

public interface TransactionConsumer extends Consumer<EntityManager> {
}
