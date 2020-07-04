package com.csangharsha.sf_movies.auth.models;

import com.csangharsha.sf_movies.domains.users.UserDto;
import lombok.Data;

@Data
public class AuthenticationResponse {

    private String accessToken;

    private UserDto user;

    public AuthenticationResponse(String accessToken, UserDto user) {
        this.accessToken = accessToken;
        this.user = user;
    }
}
