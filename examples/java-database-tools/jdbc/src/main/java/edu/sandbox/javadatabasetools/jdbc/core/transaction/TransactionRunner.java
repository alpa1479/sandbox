package edu.sandbox.javadatabasetools.jdbc.core.transaction;

import edu.sandbox.javadatabasetools.jdbc.exception.DatabaseOperationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.concurrent.Callable;

@Component
@RequiredArgsConstructor
public class TransactionRunner {

    private final DataSource dataSource;

    public <T> T transaction(TransactionFunction<T> function) {
        return wrapException(() -> {
            try (var connection = dataSource.getConnection()) {
                try {
                    var result = function.apply(connection);
                    connection.commit();
                    return result;
                } catch (SQLException ex) {
                    connection.rollback();
                    throw new DatabaseOperationException(ex);
                }
            }
        });
    }

    public void doInTransaction(TransactionConsumer action) {
        wrapException(() -> {
            try (var connection = dataSource.getConnection()) {
                try {
                    action.accept(connection);
                    connection.commit();
                    return null;
                } catch (SQLException ex) {
                    connection.rollback();
                    throw new DatabaseOperationException(ex);
                }
            }
        });
    }

    private <T> T wrapException(Callable<T> action) {
        try {
            return action.call();
        } catch (Exception ex) {
            throw new DatabaseOperationException(ex);
        }
    }
}
