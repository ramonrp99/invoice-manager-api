package com.invoicemanager.api.auth.mapper;

import com.invoicemanager.api.auth.dto.LoginRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {

    public Authentication toAuthentication(LoginRequest req) {
        return new UsernamePasswordAuthenticationToken(
                req.email(),
                req.password()
        );
    }
}
