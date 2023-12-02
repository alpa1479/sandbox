package edu.sandbox.javadatabasetools.jdbc.core.resultsetextractors;

import edu.sandbox.javadatabasetools.jdbc.exception.DatabaseOperationException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

public interface ResultSetExtractor<T> {

    Stream<T> extract(ResultSet resultSet) throws SQLException;

    default Optional<T> extractSingle(ResultSet resultSet) {
        return extractAndWrapException(resultSet).findFirst();
    }

    default Set<T> extractMultiple(ResultSet resultSet) {
        return extractAndWrapException(resultSet).collect(toSet());
    }

    private Stream<T> extractAndWrapException(ResultSet resultSet) {
        try {
            return extract(resultSet);
        } catch (SQLException e) {
            throw new DatabaseOperationException(e);
        }
    }
}
