package com.example.TaskManager.controller;


import com.example.TaskManager.dto.login.JwtResponse;
import com.example.TaskManager.dto.login.LoginRequest;
import com.example.TaskManager.dto.user.UserRequest;
import com.example.TaskManager.dto.user.UserResponse;
import com.example.TaskManager.exception.ConflictException;
import com.example.TaskManager.security.JwtUtils;
import com.example.TaskManager.security.UserPrincipal;
import com.example.TaskManager.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserService userService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse register(@Valid @RequestBody UserRequest request) {
        if (userService.existsByEmail(request.email())) {
            throw new ConflictException("Email already registered");
        }

        if (userService.existsByPhoneNumber(request.phoneNumber())) {
            throw new ConflictException("Phone number already registered");
        }

        return userService.createUser(request);
    }

    @PostMapping("/login")
    public JwtResponse login(@Valid @RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        String token = jwtUtils.generateToken(principal.getEmail());

        return new JwtResponse(
                token,
                principal.getId(),
                principal.getEmail(),
                new Date(System.currentTimeMillis() + jwtUtils.getJwtExpirationMs())
        );
    }
}