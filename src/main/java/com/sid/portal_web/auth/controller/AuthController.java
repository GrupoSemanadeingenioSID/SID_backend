package com.sid.portal_web.auth.controller;


import com.sid.portal_web.auth.service.interfaces.AuthService;
import com.sid.portal_web.dto.request.LoginRequest;
import com.sid.portal_web.dto.request.RegisterRequest;
import com.sid.portal_web.dto.response.AuthResponse;
import com.sid.portal_web.error.LoginRequestException;
import com.sid.portal_web.error.RegisterRequestException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Predicate;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        if (request.getEmail() == null || request.getEmail().isEmpty() ||
            request.getPassword() == null || request.getPassword().isEmpty()) {
            throw LoginRequestException.credentialsException();
        }
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        var errorMap = validateRegister(request);

        errorMap.forEach((predicate, errorMessage) ->
        {
            if (predicate.test(request)) {
                throw RegisterRequestException.credentialsError(errorMessage);
            }
        });

        return ResponseEntity.ok(authService.register(request));
    }


    private Map<Predicate<RegisterRequest>, String> validateRegister(RegisterRequest request) {
        Map<Predicate<RegisterRequest>, String> managementErrorMap = new LinkedHashMap<>();
        managementErrorMap.put(
                req -> req.getName() == null || req.getName().trim().isEmpty(),
                "Name is required"
        );
        managementErrorMap.put(
                req -> req.getFirst_lastName() == null || req.getFirst_lastName().trim().isEmpty(),
                "Firs last name is required"
        );
        managementErrorMap.put(
                req -> req.getSecond_lastName() == null || req.getSecond_lastName().trim().isEmpty(),
                "Second last name is required"
        );
        managementErrorMap.put(
                req -> req.getInstitutionalEmail() == null || req.getInstitutionalEmail().trim().isEmpty(),
                "Institutional email is required"
        );
        managementErrorMap.put(
                req -> req.getPassword() == null || req.getPassword().trim().isEmpty(),
                "Password is required"
        );

        return managementErrorMap;
    }

}
