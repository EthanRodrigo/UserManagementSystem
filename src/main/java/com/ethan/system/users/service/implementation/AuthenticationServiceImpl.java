package com.ethan.system.users.service.implementation;

import com.ethan.system.users.dto.JWTAuthenticationResponse;
import com.ethan.system.users.dto.RefreshTokenRequest;
import com.ethan.system.users.dto.SignInRequest;
import com.ethan.system.users.dto.SignUpRequest;
import com.ethan.system.users.model.Role;
import com.ethan.system.users.model.User;
import com.ethan.system.users.repository.UserRepository;
import com.ethan.system.users.service.AuthenticationService;
import com.ethan.system.users.service.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    public User signup(SignUpRequest request){
        User user = new User();

        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);

        return userRepository.save(user);
    }
    public JWTAuthenticationResponse signin(SignInRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        var user = userRepository.findByEmail(
                request.getEmail()).orElseThrow(() -> new IllegalArgumentException("Invalid email or password")
        );
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

        JWTAuthenticationResponse jwtAuthenticationResponse = new JWTAuthenticationResponse();
        jwtAuthenticationResponse.setToken(jwtToken);
        jwtAuthenticationResponse.setRefreshToken(refreshToken);

        return jwtAuthenticationResponse;
    }
    public JWTAuthenticationResponse refreshToken(RefreshTokenRequest request){
        String email = jwtService.extractUsername(request.getToken());
        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalCallerException("Token invalid"));
        if(jwtService.isTokenValid(request.getToken(), user)){
            var jwtToken = jwtService.generateToken(user);

            JWTAuthenticationResponse jwtAuthenticationResponse = new JWTAuthenticationResponse();
            jwtAuthenticationResponse.setToken(jwtToken);
            jwtAuthenticationResponse.setRefreshToken(request.getToken());

            return jwtAuthenticationResponse;
        }
        return null;
    }
}
