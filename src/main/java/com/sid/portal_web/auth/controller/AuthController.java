package com.sid.portal_web.auth.controller;


import com.sid.portal_web.auth.service.interfaces.AuthService;
import com.sid.portal_web.dto.request.LoginRequest;
import com.sid.portal_web.dto.request.RegisterRequest;
import com.sid.portal_web.dto.response.AuthResponse;
import com.sid.portal_web.error.LoginRequestException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request)
    {
        if(request.getEmail() == null || request.getEmail().isEmpty() ||
           request.getPassword() == null || request.getPassword().isEmpty()) {
            throw LoginRequestException.credentialsException();
        }
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request)
    {
        return ResponseEntity.ok(authService.register(request));
    }

}
