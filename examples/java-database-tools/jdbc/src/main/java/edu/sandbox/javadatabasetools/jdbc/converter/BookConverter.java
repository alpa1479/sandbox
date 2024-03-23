package edu.sandbox.javadatabasetools.jdbc.converter;

import edu.sandbox.javadatabasetools.jdbc.model.Book;
import edu.sandbox.javadatabasetools.starter.dto.BookDto;
import org.mapstruct.Mapper;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface BookConverter {

    BookDto toBookDto(Book book);

    Book toBook(BookDto bookDto);

    List<BookDto> toBookDtoList(List<Book> books);
}
