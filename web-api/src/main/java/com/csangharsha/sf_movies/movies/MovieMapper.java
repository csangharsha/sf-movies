package com.csangharsha.sf_movies.movies;

import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    Movie toEntity(MovieDto dto);

    MovieDto toDto(Movie entity);

    List<Movie> toEntity(Iterable<MovieDto> dtoList);

    List<MovieDto> toDto(Iterable<Movie> entityList);

    List<MovieDto> toDto(Page<Movie> entityList);

}
