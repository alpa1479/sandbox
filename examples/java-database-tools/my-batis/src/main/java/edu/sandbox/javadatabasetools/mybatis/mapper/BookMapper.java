package edu.sandbox.javadatabasetools.mybatis.mapper;

import edu.sandbox.javadatabasetools.mybatis.model.Book;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface BookMapper {

    @Select("""
            select
                    books.id       as id,
                    books.title    as title,
                    authors.id     as author_id,
                    authors.name   as author_name
            from books
            left join authors on authors.id = books.author_id
            """)
    @Results(id = "bookResultMap", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "title", column = "title"),
            @Result(property = "author.id", column = "author_id"),
            @Result(property = "author.name", column = "author_name")
    })
    List<Book> findAll();

    @Select("""
            select
                    books.id       as id,
                    books.title    as title,
                    authors.id     as author_id,
                    authors.name   as author_name
            from books
            left join authors on authors.id = books.author_id
            where books.id = #{id}
            """)
    @ResultMap("bookResultMap")
    Book findById(long id);

    @Insert("insert into books(title, author_id) values (#{title}, #{author.id})")
    @Options(flushCache = Options.FlushCachePolicy.TRUE, useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void create(Book book);

    @Update("update books set title=#{title} where id = #{id}")
    void update(Book book);

    @Delete("delete from books where id = #{id}")
    void delete(long id);
}
