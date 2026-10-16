package com.invoicemanager.api.user.controller;

import com.invoicemanager.api.security.CustomUserDetails;
import com.invoicemanager.api.user.dto.ChangePasswordRequest;
import com.invoicemanager.api.user.dto.UpdateUserRequest;
import com.invoicemanager.api.user.dto.UserResponse;
import com.invoicemanager.api.user.service.UserService;

import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users/me")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public UserResponse getMe(@AuthenticationPrincipal CustomUserDetails principal) {
        return userService.findMe(principal.getUserId());
    }

    @PutMapping()
    public UserResponse updateMe(@AuthenticationPrincipal CustomUserDetails principal, @RequestBody @Valid UpdateUserRequest req) {
        return userService.updateMe(principal.getUserId(), req);
    }

    @PutMapping("/change-password")
    public void changePassword(@AuthenticationPrincipal CustomUserDetails principal, @RequestBody @Valid ChangePasswordRequest req) {
        userService.changePassword(principal.getUserId(), req);
    }
}
