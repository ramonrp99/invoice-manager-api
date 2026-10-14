package com.invoicemanager.api.user.controller;

import com.invoicemanager.api.user.dto.UpdateUserRequest;
import com.invoicemanager.api.user.dto.UserResponse;
import com.invoicemanager.api.user.service.UserService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public UserResponse getMe(@PathVariable Long id) {
        return userService.findMe(id);
    }

    @PutMapping("/{id}")
    public UserResponse updateMe(@PathVariable Long id, @RequestBody @Valid UpdateUserRequest user) {
        return userService.updateMe(id, user);
    }
}
