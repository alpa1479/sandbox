package edu.sandbox.javadatabasetools.springjdbc.core.resultsetextractors;

import edu.sandbox.javadatabasetools.springjdbc.model.Book;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.Set;

public class BookMultipleResultSetExtractor implements ResultSetExtractor<Set<Book>> {

    @Override
    public Set<Book> extractData(ResultSet resultSet) throws SQLException {
        var singleResultSetExtractor = new BookSingleResultSetExtractor();

        Set<Book> books = new LinkedHashSet<>();
        var book = singleResultSetExtractor.extractData(resultSet);
        var next = book.isPresent();
        while (next) {
            books.add(book.get());
            book = singleResultSetExtractor.extractData(resultSet);
            next = book.isPresent();
        }
        return books;
    }
}
