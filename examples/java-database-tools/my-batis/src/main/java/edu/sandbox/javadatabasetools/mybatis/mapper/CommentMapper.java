package edu.sandbox.javadatabasetools.mybatis.mapper;

import edu.sandbox.javadatabasetools.mybatis.model.Comment;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Set;

@Mapper
public interface CommentMapper {

    @Select("""
            <script>
                select id,
                       text,
                       book_id
                from comments
                where book_id in <foreach item='bookId' index='index' collection='bookIds' open='(' separator=',' close=')'>#{bookId}</foreach>;
            </script>
            """)
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "text", column = "text"),
            @Result(property = "bookId", column = "book_id"),
    })
    Set<Comment> findBookComments(@Param("bookIds") long... bookIds);

    @Insert("insert into comments(text, book_id) values (#{text}, #{bookId})")
    @Options(flushCache = Options.FlushCachePolicy.TRUE, useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void create(Comment comment);

    @Update("update comments set text=#{text} where id = #{id}")
    void update(Comment comment);

    @Delete("delete from comments where book_id = #{bookId}")
    void deleteByBookId(@Param("bookId") long bookId);
}
