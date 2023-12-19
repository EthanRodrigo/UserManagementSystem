package com.ethan.system.users.service;

import com.ethan.system.users.dto.JWTAuthenticationResponse;
import com.ethan.system.users.dto.RefreshTokenRequest;
import com.ethan.system.users.dto.SignInRequest;
import com.ethan.system.users.dto.SignUpRequest;
import com.ethan.system.users.model.User;

public interface AuthenticationService {
    User signup(SignUpRequest request);
    JWTAuthenticationResponse login(SignInRequest request);
    JWTAuthenticationResponse refreshToken(RefreshTokenRequest request);
}
