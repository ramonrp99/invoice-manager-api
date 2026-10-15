package com.invoicemanager.api.user.controller;

import com.invoicemanager.api.user.dto.AdminCreateUserRequest;
import com.invoicemanager.api.user.dto.AdminUpdateUserRequest;
import com.invoicemanager.api.user.dto.AdminUserResponse;
import com.invoicemanager.api.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/users")
public class AdminUserController {

    private final UserService userService;

    public AdminUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<AdminUserResponse> getUsers() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public AdminUserResponse getUser(@PathVariable Long id) {
        return userService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AdminUserResponse createUser(@RequestBody @Valid AdminCreateUserRequest user) {
        return userService.createByAdmin(user);
    }

    @PutMapping("/{id}")
    public AdminUserResponse updateUser(@PathVariable Long id, @RequestBody @Valid AdminUpdateUserRequest user) {
        return userService.updateByAdmin(id, user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteByAdmin(id);
    }
}
