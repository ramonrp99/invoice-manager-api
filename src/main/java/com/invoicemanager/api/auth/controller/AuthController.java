package com.invoicemanager.api.auth.controller;

import com.invoicemanager.api.auth.dto.LoginRequest;
import com.invoicemanager.api.auth.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Value("${security.cookie.name}")
    private String COOKIE_NAME;
    @Value("${security.cookie.http-only}")
    private boolean COOKIE_HTTP_ONLY;
    @Value("${security.cookie.secure}")
    private boolean COOKIE_SECURE;
    @Value("${security.cookie.max-age}")
    private int COOKIE_MAX_AGE;
    @Value("${security.cookie.same-site}")
    private String COOKIE_SAME_SITE;

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public void login(@RequestBody @Valid LoginRequest req, HttpServletResponse res) {
        String token = authService.login(req);

        res.addCookie(createAuthCookie(token));
    }

    @PostMapping("/logout")
    public void logout(HttpServletResponse res) {
        Cookie cookie = new Cookie(COOKIE_NAME, null);
        cookie.setMaxAge(0);
        cookie.setPath("/api/v1");

        res.addCookie(cookie);
    }

    private Cookie createAuthCookie(String token) {
        Cookie cookie = new Cookie(COOKIE_NAME, token);

        cookie.setHttpOnly(COOKIE_HTTP_ONLY);
        cookie.setSecure(COOKIE_SECURE);
        cookie.setMaxAge(COOKIE_MAX_AGE);
        cookie.setAttribute("SameSite", COOKIE_SAME_SITE);
        cookie.setPath("/api/v1");

        return cookie;
    }
}
