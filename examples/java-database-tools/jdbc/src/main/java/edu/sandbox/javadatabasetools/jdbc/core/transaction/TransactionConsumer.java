package edu.sandbox.javadatabasetools.jdbc.core.transaction;

import java.sql.Connection;
import java.util.function.Consumer;

public interface TransactionConsumer extends Consumer<Connection> {
}
