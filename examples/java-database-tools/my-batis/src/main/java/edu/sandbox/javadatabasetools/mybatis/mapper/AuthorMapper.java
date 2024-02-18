package edu.sandbox.javadatabasetools.mybatis.mapper;

import edu.sandbox.javadatabasetools.mybatis.model.Author;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface AuthorMapper {

    @Insert("insert into authors(name) values (#{name})")
    @Options(flushCache = Options.FlushCachePolicy.TRUE, useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void create(Author author);

    @Update("update authors set name=#{name} where id = #{id}")
    void update(Author author);
}
