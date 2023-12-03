package edu.sandbox.javadatabasetools.hibernate.util;

import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
import org.hibernate.engine.jdbc.internal.BasicFormatterImpl;
import org.hibernate.engine.jdbc.internal.Formatter;

import static java.lang.String.format;
import static org.springframework.util.StringUtils.hasLength;

public class HibernateSqlFormatter implements MessageFormattingStrategy {

    private static final Formatter HIBERNATE_SQL_FORMATTER = new BasicFormatterImpl();

    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String url) {
        return hasLength(sql) ? format("Hibernate: %s {elapsed: %dms}", HIBERNATE_SQL_FORMATTER.format(sql), elapsed) : "";
    }
}
