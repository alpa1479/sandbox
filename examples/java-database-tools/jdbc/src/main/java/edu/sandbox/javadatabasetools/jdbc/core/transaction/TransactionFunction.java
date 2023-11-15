package edu.sandbox.javadatabasetools.jdbc.core.transaction;

import java.sql.Connection;
import java.util.function.Function;

public interface TransactionFunction<T> extends Function<Connection, T> {
}
