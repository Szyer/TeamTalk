package com.chatApp.TeamTalk.authentication.auth;

import com.chatApp.TeamTalk.authentication.config.JWTService;
import com.chatApp.TeamTalk.authentication.user.Role;
import com.chatApp.TeamTalk.authentication.user.User;
import com.chatApp.TeamTalk.authentication.user.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JWTService service;
    public AuthenticationResponse register(RegisterBody request) {
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        repository.save(user);
        var jwtToken = service.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        user.setIsVerified(true);
        repository.save(user);
        var jwtToken = service.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

        public boolean verifyEmail(String token) {
        Claims claims = service.extractAllClaims(token);
        String email = claims.getSubject();
        User user = repository.findByEmail(email).orElseThrow();

        if (user.isIsVerified()) {

            return false; // User has already been verified
        }

        // Set user's isVerified flag to true and save to repository
        user.setIsVerified(true);
        repository.save(user);

        return true;
    }
}
