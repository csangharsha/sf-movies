package com.csangharsha.sf_movies.domains.users;

import com.csangharsha.sf_movies.domains.role.Role;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class UserDto {

    private Long id;
    private String name;
    private String username;
    private String email;
    private Set<Role> roles = new HashSet<>();

}
