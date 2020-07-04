package com.csangharsha.sf_movies.domains.users;

import com.csangharsha.sf_movies.domains.role.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    @Column(name = "username", unique = true)
    private String username;

    @Email
    @Column(name = "email", unique = true)
    private String email;

    @NotBlank
    @Column(name = "password_hash")
    private String password;

    @Column(name = "activated")
    private Boolean activated;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_name"))
    private Set<Role> roles = new HashSet<>();

    public User(@NotBlank String name, @NotBlank String username, @Email String email, @NotBlank String password) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
    }

}
