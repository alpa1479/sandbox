package edu.sandbox.javadatabasetools.jooq.converter;

import edu.sandbox.javadatabasetools.jooq.generated.tables.pojos.Genres;
import edu.sandbox.javadatabasetools.starter.dto.GenreDto;
import org.mapstruct.Mapper;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface GenreConverter {

    GenreDto toGenreDto(Genres genre);

    Genres toGenre(GenreDto genre);

    List<Genres> toGenres(List<GenreDto> genres);
}
