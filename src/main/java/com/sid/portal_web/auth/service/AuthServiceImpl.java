package com.sid.portal_web.auth.service;


import com.sid.portal_web.auth.service.interfaces.AuthService;
import com.sid.portal_web.dto.request.LoginRequest;
import com.sid.portal_web.dto.request.RegisterRequest;
import com.sid.portal_web.dto.response.AuthResponse;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {



    @Override
    public AuthResponse login(LoginRequest loginRequest) {


        return null;
    }

    @Override
    public AuthResponse register(RegisterRequest registerRequest) {
        return null;
    }
}
