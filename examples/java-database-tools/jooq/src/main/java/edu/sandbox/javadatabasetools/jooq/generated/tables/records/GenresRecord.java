/*
 * This file is generated by jOOQ.
 */
package edu.sandbox.javadatabasetools.jooq.generated.tables.records;


import edu.sandbox.javadatabasetools.jooq.generated.tables.Genres;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class GenresRecord extends UpdatableRecordImpl<GenresRecord> implements Record2<Long, String> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.genres.id</code>.
     */
    public GenresRecord setId(Long value) {
        set(0, value);
        return this;
    }

    /**
     * Getter for <code>public.genres.id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>public.genres.name</code>.
     */
    public GenresRecord setName(String value) {
        set(1, value);
        return this;
    }

    /**
     * Getter for <code>public.genres.name</code>.
     */
    public String getName() {
        return (String) get(1);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record2 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row2<Long, String> fieldsRow() {
        return (Row2) super.fieldsRow();
    }

    @Override
    public Row2<Long, String> valuesRow() {
        return (Row2) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return Genres.GENRES.ID;
    }

    @Override
    public Field<String> field2() {
        return Genres.GENRES.NAME;
    }

    @Override
    public Long component1() {
        return getId();
    }

    @Override
    public String component2() {
        return getName();
    }

    @Override
    public Long value1() {
        return getId();
    }

    @Override
    public String value2() {
        return getName();
    }

    @Override
    public GenresRecord value1(Long value) {
        setId(value);
        return this;
    }

    @Override
    public GenresRecord value2(String value) {
        setName(value);
        return this;
    }

    @Override
    public GenresRecord values(Long value1, String value2) {
        value1(value1);
        value2(value2);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached GenresRecord
     */
    public GenresRecord() {
        super(Genres.GENRES);
    }

    /**
     * Create a detached, initialised GenresRecord
     */
    public GenresRecord(Long id, String name) {
        super(Genres.GENRES);

        setId(id);
        setName(name);
    }

    /**
     * Create a detached, initialised GenresRecord
     */
    public GenresRecord(edu.sandbox.javadatabasetools.jooq.generated.tables.pojos.Genres value) {
        super(Genres.GENRES);

        if (value != null) {
            setId(value.getId());
            setName(value.getName());
        }
    }
}
