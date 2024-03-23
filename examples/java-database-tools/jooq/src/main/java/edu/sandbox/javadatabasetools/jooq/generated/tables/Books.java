/*
 * This file is generated by jOOQ.
 */
package edu.sandbox.javadatabasetools.jooq.generated.tables;


import edu.sandbox.javadatabasetools.jooq.generated.Keys;
import edu.sandbox.javadatabasetools.jooq.generated.Public;
import edu.sandbox.javadatabasetools.jooq.generated.tables.records.BooksRecord;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row3;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;

import java.util.Arrays;
import java.util.List;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class Books extends TableImpl<BooksRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.books</code>
     */
    public static final Books BOOKS = new Books();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<BooksRecord> getRecordType() {
        return BooksRecord.class;
    }

    /**
     * The column <code>public.books.id</code>.
     */
    public final TableField<BooksRecord, Long> ID = createField(DSL.name("id"), SQLDataType.BIGINT.nullable(false).identity(true), this, "");

    /**
     * The column <code>public.books.title</code>.
     */
    public final TableField<BooksRecord, String> TITLE = createField(DSL.name("title"), SQLDataType.VARCHAR(128).nullable(false), this, "");

    /**
     * The column <code>public.books.author_id</code>.
     */
    public final TableField<BooksRecord, Long> AUTHOR_ID = createField(DSL.name("author_id"), SQLDataType.BIGINT.nullable(false), this, "");

    private Books(Name alias, Table<BooksRecord> aliased) {
        this(alias, aliased, null);
    }

    private Books(Name alias, Table<BooksRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.books</code> table reference
     */
    public Books(String alias) {
        this(DSL.name(alias), BOOKS);
    }

    /**
     * Create an aliased <code>public.books</code> table reference
     */
    public Books(Name alias) {
        this(alias, BOOKS);
    }

    /**
     * Create a <code>public.books</code> table reference
     */
    public Books() {
        this(DSL.name("books"), null);
    }

    public <O extends Record> Books(Table<O> child, ForeignKey<O, BooksRecord> key) {
        super(child, key, BOOKS);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public Identity<BooksRecord, Long> getIdentity() {
        return (Identity<BooksRecord, Long>) super.getIdentity();
    }

    @Override
    public UniqueKey<BooksRecord> getPrimaryKey() {
        return Keys.BOOKS_PKEY;
    }

    @Override
    public List<UniqueKey<BooksRecord>> getUniqueKeys() {
        return Arrays.asList(Keys.BOOKS_AUTHOR_ID_KEY);
    }

    @Override
    public List<ForeignKey<BooksRecord, ?>> getReferences() {
        return Arrays.asList(Keys.BOOKS__BOOKS_AUTHOR_ID_FKEY);
    }

    private transient Authors _authors;

    /**
     * Get the implicit join path to the <code>public.authors</code> table.
     */
    public Authors authors() {
        if (_authors == null)
            _authors = new Authors(this, Keys.BOOKS__BOOKS_AUTHOR_ID_FKEY);

        return _authors;
    }

    @Override
    public Books as(String alias) {
        return new Books(DSL.name(alias), this);
    }

    @Override
    public Books as(Name alias) {
        return new Books(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Books rename(String name) {
        return new Books(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Books rename(Name name) {
        return new Books(name, null);
    }

    // -------------------------------------------------------------------------
    // Row3 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row3<Long, String, Long> fieldsRow() {
        return (Row3) super.fieldsRow();
    }
}