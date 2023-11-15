package edu.sandbox.javadatabasetools.jdbc.core.resultsetextractors;

import edu.sandbox.javadatabasetools.jdbc.exception.DatabaseOperationException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

public interface ResultSetExtractor<T> {

    T extract(ResultSet resultSet) throws SQLException;

    default Optional<T> extractSingle(ResultSet resultSet) {
        try {
            return Optional.ofNullable(extract(resultSet));
        } catch (SQLException e) {
            throw new DatabaseOperationException(e);
        }
    }

    default Set<T> extractMultiple(ResultSet resultSet) {
        Set<T> items = new LinkedHashSet<>();
        var item = extractSingle(resultSet);
        var next = item.isPresent();
        while (next) {
            items.add(item.get());
            item = extractSingle(resultSet);
            next = item.isPresent();
        }
        return items;
    }
}
