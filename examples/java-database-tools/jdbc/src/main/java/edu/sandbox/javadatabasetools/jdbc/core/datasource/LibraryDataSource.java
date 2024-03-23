package edu.sandbox.javadatabasetools.jdbc.core.datasource;

import com.zaxxer.hikari.HikariDataSource;
import net.sf.log4jdbc.ConnectionSpy;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
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
    private final DataSource dataSource;

    public LibraryDataSource(DataSourceProperties properties) {
        this.properties = properties;
        this.dataSource = createDataSource();
    }

    private DataSource createDataSource() {
        var hikariDataSource = new HikariDataSource();

        hikariDataSource.setJdbcUrl(properties.getUrl());
        hikariDataSource.setUsername(properties.getUsername());
        hikariDataSource.setPassword(properties.getPassword());

        return hikariDataSource;
    }

    @Override
    public Connection getConnection() throws SQLException {
        var connection = dataSource.getConnection();
        connection.setAutoCommit(false);
        connection = new ConnectionSpy(connection);
        return connection;
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        var connection = dataSource.getConnection(username, password);
        connection.setAutoCommit(false);
        connection = new ConnectionSpy(connection);
        return connection;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return dataSource.getLogWriter();
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        dataSource.setLogWriter(out);
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        dataSource.setLoginTimeout(seconds);
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return dataSource.getLoginTimeout();
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return dataSource.unwrap(iface);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return dataSource.isWrapperFor(iface);
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return dataSource.getParentLogger();
    }
}
