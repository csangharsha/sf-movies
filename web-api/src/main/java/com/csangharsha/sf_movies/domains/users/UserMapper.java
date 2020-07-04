package com.csangharsha.sf_movies.domains.users;

import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserDto dto);

    UserDto toDto(User entity);

    List<User> toEntity(Iterable<UserDto> dtoList);

    List<UserDto> toDto(Iterable<User> entityList);

    List<UserDto> toDto(Page<User> entityList);


}
