package com.invoicemanager.api.auth.service;

import com.invoicemanager.api.auth.dto.LoginRequest;

public interface AuthService {

    String login(LoginRequest req);
}
