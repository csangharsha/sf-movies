package com.csangharsha.sf_movies.auth.models;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AuthenticationRequest {

    @NotBlank
    private String userNameOrEmail;

    @NotBlank
    private String password;
}
