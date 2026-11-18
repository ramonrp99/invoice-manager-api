package com.invoicemanager.api.auth.service.impl;

import com.invoicemanager.api.auth.dto.LoginRequest;
import com.invoicemanager.api.auth.mapper.AuthMapper;
import com.invoicemanager.api.auth.service.AuthService;
import com.invoicemanager.api.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final AuthMapper authMapper;
    private final JwtUtil jwtUtil;

    public AuthServiceImpl(AuthenticationManager authenticationManager, AuthMapper authMapper, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.authMapper = authMapper;
        this.jwtUtil = jwtUtil;
    }

    public String login(LoginRequest req) {
        Authentication auth = authenticationManager.authenticate(authMapper.toAuthentication(req));

        return jwtUtil.generateToken(auth.getName());
    }
}
