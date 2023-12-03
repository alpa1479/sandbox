package edu.sandbox.javadatabasetools.jdbc.core.datasource;

import edu.sandbox.javadatabasetools.jdbc.config.properties.DataSourceProperties;
import net.sf.log4jdbc.ConnectionSpy;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

@Component
public class LibraryDataSource implements DataSource {

    private final DataSourceProperties properties;
    private final DataSource source;

    public LibraryDataSource(DataSourceProperties properties) {
        this.properties = properties;
        this.source = createDataSource();
    }

    private DataSource createDataSource() {
        var pgSimpleDataSource = new PGSimpleDataSource();

        pgSimpleDataSource.setUrl(properties.url());
        pgSimpleDataSource.setUser(properties.username());
        pgSimpleDataSource.setPassword(properties.password());

        return pgSimpleDataSource;
    }

    @Override
    public Connection getConnection() throws SQLException {
        var connection = source.getConnection();
        connection.setAutoCommit(false);
        connection = new ConnectionSpy(connection);
        return connection;
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        var connection = source.getConnection(username, password);
        connection.setAutoCommit(false);
        connection = new ConnectionSpy(connection);
        return connection;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return source.getLogWriter();
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        source.setLogWriter(out);
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        source.setLoginTimeout(seconds);
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return source.getLoginTimeout();
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return source.unwrap(iface);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return source.isWrapperFor(iface);
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return source.getParentLogger();
    }
}
