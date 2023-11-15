package edu.sandbox.javadatabasetools.hibernate.core.transaction;

import jakarta.persistence.EntityManager;

import java.util.function.Function;

public interface TransactionFunction<T> extends Function<EntityManager, T> {
}
