package edu.sandbox.javadatabasetools.springdatajdbc.resultsetextractor;

import edu.sandbox.javadatabasetools.springdatajdbc.model.Book;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class BookWithReferencesListResultSetExtractor extends AbstractBookWithReferencesResultSetExtractor implements ResultSetExtractor<List<Book>> {

    @Override
    public List<Book> extractData(ResultSet rs) throws SQLException {
        return super.extract(rs).toList();
    }
}
