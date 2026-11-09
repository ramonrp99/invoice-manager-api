package com.invoicemanager.api.user.controller;

import com.invoicemanager.api.user.dto.AdminCreateUserRequest;
import com.invoicemanager.api.user.dto.AdminSearchUserRequest;
import com.invoicemanager.api.user.dto.AdminUpdateUserRequest;
import com.invoicemanager.api.user.dto.AdminUserResponse;
import com.invoicemanager.api.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/users")
public class AdminUserController {

    private final UserService userService;

    public AdminUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Page<AdminUserResponse> getUsers(@Valid AdminSearchUserRequest req) {
        return userService.findAll(req);
    }

    @GetMapping("/{id}")
    public AdminUserResponse getUser(@PathVariable Long id) {
        return userService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AdminUserResponse createUser(@RequestBody @Valid AdminCreateUserRequest req) {
        return userService.createByAdmin(req);
    }

    @PutMapping("/{id}")
    public AdminUserResponse updateUser(@PathVariable Long id, @RequestBody @Valid AdminUpdateUserRequest req) {
        return userService.updateByAdmin(id, req);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteByAdmin(id);
    }
}
