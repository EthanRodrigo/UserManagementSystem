package com.ethan.system.users.controller;

import com.ethan.system.users.dto.JWTAuthenticationResponse;
import com.ethan.system.users.dto.RefreshTokenRequest;
import com.ethan.system.users.dto.SignInRequest;
import com.ethan.system.users.dto.SignUpRequest;
import com.ethan.system.users.model.User;
import com.ethan.system.users.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    private ResponseEntity<User> signupUser(@RequestBody SignUpRequest request) {
        return ResponseEntity.ok(authenticationService.signup(request));
    }
    @PostMapping("/login")
    private ResponseEntity<JWTAuthenticationResponse> userLogin(@RequestBody SignInRequest request){
        return ResponseEntity.ok(authenticationService.login(request));
    }
    @PostMapping("/refresh")
    private ResponseEntity<JWTAuthenticationResponse> refreshToken(@RequestBody RefreshTokenRequest request){
        return ResponseEntity.ok(authenticationService.refreshToken(request));
    }
}
