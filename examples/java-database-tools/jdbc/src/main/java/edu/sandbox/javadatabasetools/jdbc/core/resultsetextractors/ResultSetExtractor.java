package edu.sandbox.javadatabasetools.jdbc.core.resultsetextractors;

import edu.sandbox.javadatabasetools.jdbc.exception.DatabaseOperationException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public interface ResultSetExtractor<T> {

    Stream<T> extract(ResultSet resultSet) throws SQLException;

    default Optional<T> extractSingle(ResultSet resultSet) {
        return extractAndWrapException(resultSet).findFirst();
    }

    default List<T> extractMultiple(ResultSet resultSet) {
        return extractAndWrapException(resultSet).collect(toList());
    }

    private Stream<T> extractAndWrapException(ResultSet resultSet) {
        try {
            return extract(resultSet);
        } catch (SQLException e) {
            throw new DatabaseOperationException(e);
        }
    }
}
