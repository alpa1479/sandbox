/*
 * This file is generated by jOOQ.
 */
package edu.sandbox.javadatabasetools.jooq.generated.tables.daos;


import edu.sandbox.javadatabasetools.jooq.generated.tables.Comments;
import edu.sandbox.javadatabasetools.jooq.generated.tables.records.CommentsRecord;
import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({"all", "unchecked", "rawtypes"})
@Repository
public class CommentsDao extends DAOImpl<CommentsRecord, edu.sandbox.javadatabasetools.jooq.generated.tables.pojos.Comments, Long> {

    /**
     * Create a new CommentsDao without any configuration
     */
    public CommentsDao() {
        super(Comments.COMMENTS, edu.sandbox.javadatabasetools.jooq.generated.tables.pojos.Comments.class);
    }

    /**
     * Create a new CommentsDao with an attached configuration
     */
    @Autowired
    public CommentsDao(Configuration configuration) {
        super(Comments.COMMENTS, edu.sandbox.javadatabasetools.jooq.generated.tables.pojos.Comments.class, configuration);
    }

    @Override
    public Long getId(edu.sandbox.javadatabasetools.jooq.generated.tables.pojos.Comments object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    public List<edu.sandbox.javadatabasetools.jooq.generated.tables.pojos.Comments> fetchRangeOfId(Long lowerInclusive, Long upperInclusive) {
        return fetchRange(Comments.COMMENTS.ID, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<edu.sandbox.javadatabasetools.jooq.generated.tables.pojos.Comments> fetchById(Long... values) {
        return fetch(Comments.COMMENTS.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public edu.sandbox.javadatabasetools.jooq.generated.tables.pojos.Comments fetchOneById(Long value) {
        return fetchOne(Comments.COMMENTS.ID, value);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public Optional<edu.sandbox.javadatabasetools.jooq.generated.tables.pojos.Comments> fetchOptionalById(Long value) {
        return fetchOptional(Comments.COMMENTS.ID, value);
    }

    /**
     * Fetch records that have <code>book_id BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    public List<edu.sandbox.javadatabasetools.jooq.generated.tables.pojos.Comments> fetchRangeOfBookId(Long lowerInclusive, Long upperInclusive) {
        return fetchRange(Comments.COMMENTS.BOOK_ID, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>book_id IN (values)</code>
     */
    public List<edu.sandbox.javadatabasetools.jooq.generated.tables.pojos.Comments> fetchByBookId(Long... values) {
        return fetch(Comments.COMMENTS.BOOK_ID, values);
    }

    /**
     * Fetch records that have <code>text BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    public List<edu.sandbox.javadatabasetools.jooq.generated.tables.pojos.Comments> fetchRangeOfText(String lowerInclusive, String upperInclusive) {
        return fetchRange(Comments.COMMENTS.TEXT, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>text IN (values)</code>
     */
    public List<edu.sandbox.javadatabasetools.jooq.generated.tables.pojos.Comments> fetchByText(String... values) {
        return fetch(Comments.COMMENTS.TEXT, values);
    }
}
