package com.csangharsha.sf_movies.auth;

import com.csangharsha.sf_movies.auth.jwt.JwtTokenProvider;
import com.csangharsha.sf_movies.auth.models.ApiResponse;
import com.csangharsha.sf_movies.auth.models.AuthenticationRequest;
import com.csangharsha.sf_movies.auth.models.AuthenticationResponse;
import com.csangharsha.sf_movies.auth.models.RegistrationRequest;
import com.csangharsha.sf_movies.domains.role.Role;
import com.csangharsha.sf_movies.domains.role.RoleRepository;
import com.csangharsha.sf_movies.domains.users.User;
import com.csangharsha.sf_movies.domains.users.UserDto;
import com.csangharsha.sf_movies.domains.users.UserMapper;
import com.csangharsha.sf_movies.domains.users.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthResource {

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider tokenProvider;

    private final UserMapper userMapper;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody AuthenticationRequest authenticationRequest) {

        log.info("Authenticating user {} ", authenticationRequest.getUserNameOrEmail());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUserNameOrEmail(),
                        authenticationRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        log.info("Generating token");

        String jwt = "Bearer " + tokenProvider.generateToken(authentication);
        User user = userRepository.findByUsername(((UserPrincipal) authentication.getPrincipal()).getUsername()).
                orElseThrow(() -> new UsernameNotFoundException("User doesn't exist"));

        UserDto userDTO = userMapper.toDto(user);

        log.info("Token generated for user {} ", userDTO.getUsername());

        return ResponseEntity.ok(new AuthenticationResponse(jwt, userDTO));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegistrationRequest registrationRequest) {
        if (userRepository.existsByUsername(registrationRequest.getUserName())) {
            return new ResponseEntity<>(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByEmail(registrationRequest.getEmail())) {
            return new ResponseEntity<>(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        User result = createUserAccount(registrationRequest);

        userRepository.save(result);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }

    public User createUserAccount(@RequestBody @Valid RegistrationRequest registrationRequest) {
        User user = new User(registrationRequest.getName(), registrationRequest.getUserName(),
                registrationRequest.getEmail(), registrationRequest.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActivated(true);

        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElse(roleRepository.save(new Role("ROLE_USER")));

        user.getRoles().add(userRole);

        return user;
    }
}