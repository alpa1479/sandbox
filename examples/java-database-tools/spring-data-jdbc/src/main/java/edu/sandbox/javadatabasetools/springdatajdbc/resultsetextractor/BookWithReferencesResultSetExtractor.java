package edu.sandbox.javadatabasetools.springdatajdbc.resultsetextractor;

import edu.sandbox.javadatabasetools.springdatajdbc.model.Book;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class BookWithReferencesResultSetExtractor extends AbstractBookWithReferencesResultSetExtractor implements ResultSetExtractor<Optional<Book>> {

    @Override
    public Optional<Book> extractData(ResultSet rs) throws SQLException {
        return super.extract(rs).findFirst();
    }
}
