package edu.sandbox.javadatabasetools.jooq.converter;

import edu.sandbox.javadatabasetools.jooq.generated.tables.pojos.Books;
import edu.sandbox.javadatabasetools.starter.dto.BookDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface BookConverter {

    BookDto toBookDto(Books book);

    @Mapping(target = "authorId", source = "author.id")
    Books toBook(BookDto bookDto);

    List<BookDto> toBookDtoList(List<Books> books);
}
