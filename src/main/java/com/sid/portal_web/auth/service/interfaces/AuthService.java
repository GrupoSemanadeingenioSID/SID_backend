package com.sid.portal_web.auth.service.interfaces;


import com.sid.portal_web.dto.request.LoginRequest;
import com.sid.portal_web.dto.request.RegisterRequest;
import com.sid.portal_web.dto.response.AuthResponse;

public interface AuthService {

    AuthResponse login(LoginRequest loginRequest);
    AuthResponse register(RegisterRequest registerRequest);

}
