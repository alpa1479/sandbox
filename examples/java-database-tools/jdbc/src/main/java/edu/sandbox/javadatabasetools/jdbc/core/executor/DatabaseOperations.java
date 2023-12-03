package edu.sandbox.javadatabasetools.jdbc.core.executor;

import edu.sandbox.javadatabasetools.jdbc.core.resultsetextractors.ResultSetExtractor;
import edu.sandbox.javadatabasetools.jdbc.exception.DatabaseOperationException;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;

import static org.springframework.util.CollectionUtils.isEmpty;

@Component
public class DatabaseOperations {

    public long execute(Connection connection, String sql, Object... params) {
        return execute(connection, sql, Arrays.asList(params));
    }

    public <T> Optional<T> selectSingle(Connection connection, ResultSetExtractor<T> resultSetExtractor, String sql, Object... params) {
        return doSelectSingle(connection, resultSetExtractor, sql, Arrays.asList(params));
    }

    public <T> Optional<T> selectSingle(Connection connection, ResultSetExtractor<T> resultSetExtractor, String sql) {
        return doSelectSingle(connection, resultSetExtractor, sql, null);
    }

    public <T> Set<T> selectMultiple(Connection connection, ResultSetExtractor<T> resultSetExtractor, String sql, Object... params) {
        return doSelectMultiple(connection, resultSetExtractor, sql, Arrays.asList(params));
    }

    public <T> Set<T> selectMultiple(Connection connection, ResultSetExtractor<T> resultSetExtractor, String sql) {
        return doSelectMultiple(connection, resultSetExtractor, sql, null);
    }

    private <T> Optional<T> doSelectSingle(Connection connection, ResultSetExtractor<T> resultSetExtractor, String sql, List<Object> params) {
        return doSelect(connection, resultSetExtractor, ResultSetExtractor::extractSingle, sql, params);
    }

    private <T> Set<T> doSelectMultiple(Connection connection, ResultSetExtractor<T> resultSetExtractor, String sql, List<Object> params) {
        return doSelect(connection, resultSetExtractor, ResultSetExtractor::extractMultiple, sql, params);
    }

    private <T, R> R doSelect(Connection connection, ResultSetExtractor<T> resultSetExtractor,
                              BiFunction<ResultSetExtractor<T>, ResultSet, R> extractorFunction, String sql, List<Object> params) {
        try (var preparedStatement = connection.prepareStatement(sql)) {
            if (!isEmpty(params)) {
                bindParams(params, preparedStatement);
            }
            try (var resultSet = preparedStatement.executeQuery()) {
                return extractorFunction.apply(resultSetExtractor, resultSet);
            }
        } catch (SQLException e) {
            throw new DatabaseOperationException(e);
        }
    }

    private long execute(Connection connection, String sql, List<Object> params) {
        try (var preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            bindParams(params, preparedStatement);

            preparedStatement.executeUpdate();
            try (var resultSet = preparedStatement.getGeneratedKeys()) {
                resultSet.next();
                return resultSet.getLong(1);
            }

        } catch (SQLException e) {
            throw new DatabaseOperationException(e);
        }
    }

    private void bindParams(List<Object> params, PreparedStatement preparedStatement) throws SQLException {
        for (var index = 0; index < params.size(); index++) {
            preparedStatement.setObject(index + 1, params.get(index));
        }
    }
}
