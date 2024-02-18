package edu.sandbox.javadatabasetools.mybatis.mapper;

import edu.sandbox.javadatabasetools.mybatis.model.Genre;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface GenreMapper {

    @Select("""
            <script>
                select
                        genres.id               as id,
                        genres.name             as name,
                        books_genres.book_id    as book_id
                from genres
                left join books_genres on books_genres.genre_id = genres.id
                where books_genres.book_id in <foreach item='bookId' index='index' collection='bookIds' open='(' separator=',' close=')'>#{bookId}</foreach>;
            </script>
            """)
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "bookId", column = "book_id")
    })
    List<Genre> findBookGenres(@Param("bookIds") long... bookIds);

    @Insert("insert into genres(name) values (#{name})")
    @Options(flushCache = Options.FlushCachePolicy.TRUE, useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void create(Genre genre);

    @Insert("insert into books_genres(book_id, genre_id) values (#{bookId}, #{genreId})")
    void createBookGenre(@Param("bookId") long bookId, @Param("genreId") long genreId);

    @Update("update genres set name=#{name} where id = #{id}")
    void update(Genre genre);

    @Delete("delete from books_genres where book_id = #{bookId}")
    void deleteBookGenres(@Param("bookId") long bookId);
}
