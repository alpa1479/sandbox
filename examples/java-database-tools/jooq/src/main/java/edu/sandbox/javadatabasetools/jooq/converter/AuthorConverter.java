package edu.sandbox.javadatabasetools.jooq.converter;

import edu.sandbox.javadatabasetools.jooq.generated.tables.pojos.Authors;
import edu.sandbox.javadatabasetools.starter.dto.AuthorDto;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface AuthorConverter {

    AuthorDto toAuthorDto(Authors author);

    Authors toAuthor(AuthorDto authorDto);
}
