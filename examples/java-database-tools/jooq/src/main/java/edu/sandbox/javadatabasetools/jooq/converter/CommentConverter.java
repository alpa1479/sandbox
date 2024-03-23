package edu.sandbox.javadatabasetools.jooq.converter;

import edu.sandbox.javadatabasetools.jooq.generated.tables.pojos.Comments;
import edu.sandbox.javadatabasetools.starter.dto.CommentDto;
import org.mapstruct.Mapper;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface CommentConverter {

    CommentDto toCommentDto(Comments comment);

    List<CommentDto> toCommentDtoList(List<Comments> comment);

    Comments toComment(CommentDto comment);

    List<Comments> toComments(List<CommentDto> comment);
}
