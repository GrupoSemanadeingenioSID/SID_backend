package com.sid.portal_web.auth.service;


import com.sid.portal_web.auth.service.interfaces.AuthService;
import com.sid.portal_web.auth.service.interfaces.JwtService;
import com.sid.portal_web.auth.service.registerBuilder.DirectorRegister;
import com.sid.portal_web.dto.request.LoginRequest;
import com.sid.portal_web.dto.request.RegisterRequest;
import com.sid.portal_web.dto.response.AuthResponse;
import com.sid.portal_web.error.LoginRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final DirectorRegister directorRegister;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword())
        );
        UserDetails userDetails = (UserDetails) authentication.getPrincipal(); // mirar eso

        String token = jwtService.getToken(userDetails);
        return AuthResponse.builder()
                .token(token)
                .build();
    }

    @Override
    public AuthResponse register(RegisterRequest registerRequest) {
        System.out.println("Register: " + registerRequest);
        directorRegister.register(registerRequest);
        String email = directorRegister.getUser().getEmail();

        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        return AuthResponse.builder()
                .token(jwtService.getToken(userDetails))
                .refreshToken(jwtService.getRefreshToken(userDetails))
                .build();
    }
}
