package com.invoicemanager.api.user.controller;

import com.invoicemanager.api.security.CustomUserDetails;
import com.invoicemanager.api.user.dto.UpdateUserRequest;
import com.invoicemanager.api.user.dto.UserResponse;
import com.invoicemanager.api.user.service.UserService;

import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public UserResponse getMe(@AuthenticationPrincipal CustomUserDetails principal) {
        return userService.findMe(principal.getUserId());
    }

    @PutMapping("/me")
    public UserResponse updateMe(@AuthenticationPrincipal CustomUserDetails principal, @RequestBody @Valid UpdateUserRequest user) {
        return userService.updateMe(principal.getUserId(), user);
    }
}
