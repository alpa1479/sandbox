package edu.sandbox.javadatabasetools.hibernate.util;

import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
import org.hibernate.engine.jdbc.internal.FormatStyle;

import static java.lang.String.format;
import static org.springframework.util.StringUtils.hasLength;

public class HibernateSqlFormatter implements MessageFormattingStrategy {

    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String url) {
        return hasLength(sql) ?
                format("Hibernate:%n %s {elapsed: %dms}%n",
                        FormatStyle.HIGHLIGHT.getFormatter().format(FormatStyle.BASIC.getFormatter().format(sql)), elapsed) : "";
    }
}
