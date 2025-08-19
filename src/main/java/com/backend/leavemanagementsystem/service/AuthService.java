package com.backend.leavemanagementsystem.service;

import com.backend.leavemanagementsystem.repository.HrRepository;
import com.backend.leavemanagementsystem.utils.LoginRequest;
import com.backend.leavemanagementsystem.utils.LoginResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final HrRepository hrRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(HrRepository hrRepository, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.hrRepository = hrRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public LoginResponse authenticate(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        var user = hrRepository.findByUsername(request.getUsername()).orElseThrow();
        var jwt = jwtService.generateToken(user);
        return LoginResponse.builder()
                .message("Login successful")
                .name(user.getUsername())
                .token(jwt)
                .build();
    }
}
